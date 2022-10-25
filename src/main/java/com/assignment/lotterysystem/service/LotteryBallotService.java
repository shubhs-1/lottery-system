package com.assignment.lotterysystem.service;

import com.assignment.lotterysystem.exception.DataNotFoundException;

public interface LotteryBallotService {
    Long selectRandomLotteryWinner(Long lotteryId) throws DataNotFoundException;
}
