package com.assignment.lotterysystem.repository;

import com.assignment.lotterysystem.model.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shubham Kalaria
 * JPA repository interface to declare methods for database layer communication
 */
@Repository
public interface LotteryRepository extends JpaRepository<Lottery, String> {
    /**
     * Method to find lotteries which are active
     * @return List<Lottery>
     */
    List<Lottery> findLotteriesByEndDateIsNull();

    /**
     * Method to find lottery by id
     * @param id
     * @return Lottery
     */
    Lottery findById(Long id);

    /**
     * Method to count active lottery by name
     * @param name
     * @return Long
     */
    Long countByNameAndEndDateIsNull(String name);
}
