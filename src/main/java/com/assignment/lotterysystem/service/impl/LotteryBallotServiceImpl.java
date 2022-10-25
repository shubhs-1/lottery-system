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

@Log4j2
@Service
public class LotteryBallotServiceImpl implements LotteryBallotService {
    @Autowired
    private LotteryBallotRepository lotteryBallotRepository;

    @Autowired
    private ParticipantService participantService;

    @Override
    public Long selectRandomLotteryWinner(Long lotteryId) throws DataNotFoundException {
        Long count = lotteryBallotRepository.countLotteryBallotByLotteryId(lotteryId);

        if (count == 0) {
            log.info("No participation found for id: {}", lotteryId);
            return -1L;
        }
        long random = getRandom(count);
        LotteryBallot lotteryBallot = lotteryBallotRepository.findByLotteryNumberAndLotteryId(random, lotteryId);
        if (ObjectUtils.isEmpty(lotteryBallot)) {
            throw new DataNotFoundException("Lottery ballot not found for lottery number: " + random);
        }
        return lotteryBallot.getLotteryNumber();
    }

    private long getRandom(Long count) {
        Random rand = new Random();
        return rand.nextInt(count.intValue()) + 1L;
    }
}
