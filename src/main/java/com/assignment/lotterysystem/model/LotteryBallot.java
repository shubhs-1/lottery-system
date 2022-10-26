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
 * LotteryBallot entity class to communicate with database
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lottery_ballot")
public class LotteryBallot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lottery_id")
    private Long lotteryId;

    @Column(name = "username")
    private String username;

    @Column(name = "lottery_number")
    private Long lotteryNumber;

    @Column(name = "date")
    private LocalDate date;
}
