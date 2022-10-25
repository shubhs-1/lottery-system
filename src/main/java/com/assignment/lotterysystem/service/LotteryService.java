package com.assignment.lotterysystem.service;

import com.assignment.lotterysystem.dto.LotteryDto;
import com.assignment.lotterysystem.exception.DataNotFoundException;
import com.assignment.lotterysystem.exception.LotteryStatusException;
import com.assignment.lotterysystem.exception.SaveFailureException;
import com.assignment.lotterysystem.model.Lottery;
import com.assignment.lotterysystem.model.LotteryBallot;

import java.util.List;

public interface LotteryService {
    Lottery startLotteryByName(String lotteryName) throws SaveFailureException;

    Lottery findByLotteryId(Long lotteryId) throws DataNotFoundException;

    List<Lottery> getActiveLotteries();

    void endLotteryAndSelectLotteryWinner(Long lotteryId) throws LotteryStatusException, DataNotFoundException;

    void endAllActiveLotteriesAndSelectWinners();

    LotteryDto getLotteryResultByLotteryIdAndDate(Long lotteryId, String date) throws LotteryStatusException, DataNotFoundException;

    LotteryBallot submitLotteryBallot(Long lotteryId, String username) throws DataNotFoundException, LotteryStatusException;
}
