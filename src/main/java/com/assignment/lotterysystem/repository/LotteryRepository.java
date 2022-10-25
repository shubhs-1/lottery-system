package com.assignment.lotterysystem.repository;

import com.assignment.lotterysystem.model.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, String> {
    List<Lottery> findLotteriesByEndDateIsNull();

    Lottery findById(Long id);

    Long countByNameAndEndDateIsNull(String name);
}
