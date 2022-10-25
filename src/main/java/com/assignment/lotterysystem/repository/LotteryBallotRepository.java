package com.assignment.lotterysystem.repository;

import com.assignment.lotterysystem.model.LotteryBallot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryBallotRepository extends JpaRepository<LotteryBallot, Long> {
    LotteryBallot findFirstByLotteryIdOrderByLotteryNumberDesc(Long lotteryId);

    Long countLotteryBallotByLotteryId(Long lotteryId);

    LotteryBallot findByLotteryNumberAndLotteryId(Long lotteryNumber, Long lotteryId);
}
