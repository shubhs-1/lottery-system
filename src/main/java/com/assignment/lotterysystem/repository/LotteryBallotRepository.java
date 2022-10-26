package com.assignment.lotterysystem.repository;

import com.assignment.lotterysystem.model.LotteryBallot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Shubham Kalaria
 * JPA repository interface to declare methods for database layer communication
 */
@Repository
public interface LotteryBallotRepository extends JpaRepository<LotteryBallot, Long> {
    /**
     * Method to find first lotteryId by lottery number
     * @param lotteryId
     * @return LotteryBallot
     */
    LotteryBallot findFirstByLotteryIdOrderByLotteryNumberDesc(Long lotteryId);

    /**
     * Method to count lottery ballot by lotteryId
     * @param lotteryId
     * @return Long
     */
    Long countLotteryBallotByLotteryId(Long lotteryId);

    /**
     * Method to find ballot details by lotteryNumber and lotteryId
     * @param lotteryNumber
     * @param lotteryId
     * @return LotteryBallot
     */
    LotteryBallot findByLotteryNumberAndLotteryId(Long lotteryNumber, Long lotteryId);
}
