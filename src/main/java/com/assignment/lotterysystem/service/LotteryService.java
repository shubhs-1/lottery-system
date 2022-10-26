package com.assignment.lotterysystem.service;

import com.assignment.lotterysystem.dto.LotteryDto;
import com.assignment.lotterysystem.exception.DataNotFoundException;
import com.assignment.lotterysystem.exception.LotteryStatusException;
import com.assignment.lotterysystem.exception.SaveFailureException;
import com.assignment.lotterysystem.model.Lottery;
import com.assignment.lotterysystem.model.LotteryBallot;

import java.util.List;

/**
 * @author Shubham Kalaria
 * Interface to provide services related to Lottery
 */
public interface LotteryService {
    /**
     * Method to start lottery by given name
     * @param lotteryName
     * @return Lottery
     * @throws SaveFailureException
     */
    Lottery startLotteryByName(String lotteryName) throws SaveFailureException;

    /**
     * Method to get lottery details by given lotteryId
     * @param lotteryId
     * @return Lottery
     * @throws DataNotFoundException
     */
    Lottery findByLotteryId(Long lotteryId) throws DataNotFoundException;

    /**
     * Method to get all active lotteries
     * @return List<Lottery>
     */
    List<Lottery> getActiveLotteries();

    /**
     * Method to end lottery by given lotteryId and randomly select winner
     * @param lotteryId
     * @throws LotteryStatusException
     * @throws DataNotFoundException
     */
    void endLotteryAndSelectLotteryWinner(Long lotteryId) throws LotteryStatusException, DataNotFoundException;

    /**
     * Method to end all active lotteries and randomly select winners
     */
    void endAllActiveLotteriesAndSelectWinners();

    /**
     * Method to get lottery result by given lotteryId and date
     * @param lotteryId
     * @param date
     * @return LotteryDto
     * @throws LotteryStatusException
     * @throws DataNotFoundException
     */
    LotteryDto getLotteryResultByLotteryIdAndDate(Long lotteryId, String date) throws LotteryStatusException, DataNotFoundException;

    /**
     * Thread safe method to submit lottery ballot by given lotteryId and username
     * @param lotteryId
     * @param username
     * @return LotteryBallot
     * @throws DataNotFoundException
     * @throws LotteryStatusException
     */
    LotteryBallot submitLotteryBallotSync(Long lotteryId, String username) throws DataNotFoundException, LotteryStatusException;
}
