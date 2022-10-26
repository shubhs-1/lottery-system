package com.assignment.lotterysystem.service.impl;

import com.assignment.lotterysystem.dto.LotteryDto;
import com.assignment.lotterysystem.exception.DataNotFoundException;
import com.assignment.lotterysystem.exception.LotteryStatusException;
import com.assignment.lotterysystem.exception.LotterySubmissionFailed;
import com.assignment.lotterysystem.exception.SaveFailureException;
import com.assignment.lotterysystem.model.Lottery;
import com.assignment.lotterysystem.model.LotteryBallot;
import com.assignment.lotterysystem.repository.LotteryBallotRepository;
import com.assignment.lotterysystem.repository.LotteryRepository;
import com.assignment.lotterysystem.service.LotteryService;
import com.assignment.lotterysystem.service.LotteryBallotService;
import com.assignment.lotterysystem.service.ParticipantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Shubham Kalaria
 * Class to implement LotteryService interface
 */
@Log4j2
@Service
public class LotteryServiceImpl implements LotteryService {
    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private LotteryBallotRepository lotteryBallotRepository;

    @Autowired
    private LotteryBallotService lotteryBallotService;

    @Autowired
    private ParticipantService participantService;

    /**
     * Method to start lottery by given name
     * @param lotteryName
     * @return Lottery
     * @throws SaveFailureException
     */
    @Transactional
    @Override
    public Lottery startLotteryByName(String lotteryName) throws SaveFailureException {
        checkActiveLotteryWithSameName(lotteryName);

        Lottery lottery = new Lottery();
        lottery.setName(lotteryName);
        lottery.setStartDate(LocalDate.now());
        return lotteryRepository.save(lottery);
    }

    /**
     * Method to check for active lotteries with given name
     * @param lotteryName
     * @throws SaveFailureException
     */
    private void checkActiveLotteryWithSameName(String lotteryName) throws SaveFailureException {
        Long count = lotteryRepository.countByNameAndEndDateIsNull(lotteryName);

        if (count > 0) {
            throw new SaveFailureException("Active lottery already exists by name: " + lotteryName);
        }
    }

    /**
     * Method to get lottery details by given lotteryId
     * @param lotteryId
     * @return Lottery
     * @throws DataNotFoundException
     */
    @Override
    public Lottery findByLotteryId(Long lotteryId) throws DataNotFoundException {
        Lottery lottery = lotteryRepository.findById(lotteryId);
        if (ObjectUtils.isEmpty(lottery)) {
            throw new DataNotFoundException("Lottery not found for id: " + lotteryId);
        }
        return lottery;
    }

    /**
     * Method to end all active lotteries and randomly select winners
     */
    @Override
    public void endAllActiveLotteriesAndSelectWinners() {
        List<Lottery> lotteries = getActiveLotteries();
        lotteries.stream().forEach(lottery -> {
            try {
                endLotteryAndSelectLotteryWinner(lottery.getId());
            } catch (DataNotFoundException | LotteryStatusException e) {
                log.error("Lottery couldn't end for id: {}, error: {} ", lottery.getId(), e.getMessage());
            }
        });
    }

    /**
     * Method to get all active lotteries
     * @return List<Lottery>
     */
    @Override
    public List<Lottery> getActiveLotteries() {
        return lotteryRepository.findLotteriesByEndDateIsNull();
    }

    /**
     * Method to end lottery by given lotteryId and randomly select winner
     * @param lotteryId
     * @throws LotteryStatusException
     * @throws DataNotFoundException
     */
    @Transactional
    @Override
    public void endLotteryAndSelectLotteryWinner(Long lotteryId) throws LotteryStatusException, DataNotFoundException {
        checkLotteryIsFinished(lotteryId);
        Long winnerNumber = lotteryBallotService.selectRandomLotteryWinner(lotteryId);
        endLotteryAndSaveLotteryResult(lotteryId, winnerNumber);
    }

    /**
     * Helper method to end lottery and save the result
     * @param lotteryId
     * @param winnerLotteryNum
     * @throws DataNotFoundException
     */
    private void endLotteryAndSaveLotteryResult(Long lotteryId, Long winnerLotteryNum) throws DataNotFoundException {
        Lottery lottery = findByLotteryId(lotteryId);
        lottery.setLotteryWinner(winnerLotteryNum);
        lottery.setEndDate(LocalDate.now());
        lotteryRepository.save(lottery);
    }

    /**
     * Method to get lottery result by given lotteryId and date
     * @param lotteryId
     * @param date
     * @return LotteryDto
     * @throws LotteryStatusException
     * @throws DataNotFoundException
     */
    @Override
    public LotteryDto getLotteryResultByLotteryIdAndDate(Long lotteryId, String date) throws LotteryStatusException, DataNotFoundException {
        checkLotteryIsActive(lotteryId);
        Lottery lottery = findByLotteryId(lotteryId);
        String winner = (lottery.getLotteryWinner() == -1) ? "Nobody won!" : lottery.getLotteryWinner().toString();

        String endDate = lottery.getEndDate().toString();

        if(!date.equals(endDate)) {
            throw new DataNotFoundException("Winning ballot not found for specified date: " + date);
        }

        return new LotteryDto(endDate, winner);
    }

    /**
     * Helper method to check if the lottery is active or finished for given lotteryID
     * @param lotteryId
     * @throws LotteryStatusException
     * @throws DataNotFoundException
     */
    private void checkLotteryIsFinished(Long lotteryId) throws LotteryStatusException, DataNotFoundException {
        Lottery lottery = findByLotteryId(lotteryId);
        if (!ObjectUtils.isEmpty(lottery.getEndDate())) {
            throw new LotteryStatusException("Lottery has been finished for id: " + lottery.getId());
        }
    }

    /**
     * Helper method to check if the lottery is active for given lotteryId
     * @param lotteryId
     * @throws LotteryStatusException
     * @throws DataNotFoundException
     */
    private void checkLotteryIsActive(Long lotteryId) throws LotteryStatusException, DataNotFoundException {
        Lottery lottery = findByLotteryId(lotteryId);
        if (ObjectUtils.isEmpty(lottery.getEndDate())) {
            throw new LotteryStatusException("Lottery not yet finished for id: " + lotteryId);
        }
    }

    /**
     * Thread safe method to submit lottery ballot by given lotteryId and username
     * @param lotteryId
     * @param username
     * @return LotteryBallot
     * @throws DataNotFoundException
     * @throws LotteryStatusException
     */
    @Override
    public synchronized LotteryBallot submitLotteryBallot(Long lotteryId, String username) throws DataNotFoundException, LotteryStatusException {
        checkLotteryIsFinished(lotteryId);
        validateUsername(username);

        LotteryBallot lotteryBallot = new LotteryBallot();
        lotteryBallot.setLotteryId(lotteryId);
        lotteryBallot.setLotteryNumber(generateLotteryNumber(lotteryId));
        lotteryBallot.setUsername(username);
        lotteryBallot.setDate(LocalDate.now());
        lotteryBallotRepository.save(lotteryBallot);
        return lotteryBallot;
    }

    /**
     * Helper method to validate username
     * @param username
     * @throws DataNotFoundException
     */
    private void validateUsername(String username) throws DataNotFoundException {
        if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(participantService.findParticipantByUsername(username))) {
            throw new DataNotFoundException("Username not found: " + username);
        }
    }

    /**
     * Helper method to generate next lottery number
     * @param lotteryId
     * @return
     */
    private Long generateLotteryNumber(Long lotteryId) {
        LotteryBallot lotteryBallot = lotteryBallotRepository.findFirstByLotteryIdOrderByLotteryNumberDesc(lotteryId);
        return ObjectUtils.isEmpty(lotteryBallot) ? 1L : lotteryBallot.getLotteryNumber() + 1;
    }
}
