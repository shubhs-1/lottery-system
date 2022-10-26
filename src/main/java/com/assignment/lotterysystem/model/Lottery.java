package com.assignment.lotterysystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Shubham Kalaria
 * Lottery entity class to communicate with database
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lottery_details")
public class Lottery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lottery_name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "lottery_winner")
    private Long lotteryWinner;
}
