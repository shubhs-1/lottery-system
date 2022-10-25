package com.assignment.lotterysystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotteryDto implements Serializable {
    private String date;
    private String lotteryWinner;
}
