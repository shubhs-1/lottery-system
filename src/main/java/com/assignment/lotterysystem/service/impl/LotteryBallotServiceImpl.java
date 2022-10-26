package com.assignment.lotterysystem.service.impl;

import com.assignment.lotterysystem.exception.DataNotFoundException;
import com.assignment.lotterysystem.model.LotteryBallot;
import com.assignment.lotterysystem.repository.LotteryBallotRepository;
import com.assignment.lotterysystem.service.LotteryBallotService;
import com.assignment.lotterysystem.service.ParticipantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Random;

/**
 * @author Shubham Kalaria
 * Class to implement LotteryBallotService interface
 */
@Log4j2
@Service
public class LotteryBallotServiceImpl implements LotteryBallotService {
    @Autowired
    private LotteryBallotRepository lotteryBallotRepository;

    @Autowired
    private ParticipantService participantService;

    /**
     * Method to select random lottery number as winner for given lotteryId
     * @param lotteryId
     * @return Long
     * @throws DataNotFoundException
     */
    @Override
    public Long selectRandomLotteryWinner(Long lotteryId) throws DataNotFoundException {
        Long count = lotteryBallotRepository.countLotteryBallotByLotteryId(lotteryId);

        if (count == 0) {
            log.error("No participation found for id: {}", lotteryId);
            return -1L;
        }
        long random = getRandom(count);
        LotteryBallot lotteryBallot = lotteryBallotRepository.findByLotteryNumberAndLotteryId(random, lotteryId);
        if (ObjectUtils.isEmpty(lotteryBallot)) {
            log.error("Lottery ballot not found for lottery number: {}", random);
            throw new DataNotFoundException("Lottery ballot not found for lottery number: " + random);
        }
        log.info("Successfully found lottery winner for id: {}", lotteryId);
        return lotteryBallot.getLotteryNumber();
    }

    /**
     * Helper method to generate random number
     * @param count
     * @return long
     */
    private long getRandom(Long count) {
        Random rand = new Random();
        return rand.nextInt(count.intValue()) + 1L;
    }
}
