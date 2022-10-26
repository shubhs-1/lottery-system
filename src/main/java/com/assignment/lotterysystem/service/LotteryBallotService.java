package com.assignment.lotterysystem.service;

import com.assignment.lotterysystem.exception.DataNotFoundException;

/**
 * @author Shubham Kalaria
 * Interface to provide services related to LotteryBallot
 */
public interface LotteryBallotService {
    /**
     * Method to select random lottery number as winner for given lotteryId
     * @param lotteryId
     * @return Long
     * @throws DataNotFoundException
     */
    Long selectRandomLotteryWinner(Long lotteryId) throws DataNotFoundException;
}
