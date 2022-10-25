package com.assignment.lotterysystem.controller;

import com.assignment.lotterysystem.dto.RestResponse;
import com.assignment.lotterysystem.exception.DataNotFoundException;
import com.assignment.lotterysystem.exception.LotteryStatusException;
import com.assignment.lotterysystem.exception.SaveFailureException;
import com.assignment.lotterysystem.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/lottery")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    // at 12:00 AM every day
    @Scheduled(cron = "0 0 0 * * ?")
    @PostMapping("/endActiveLotteries")
    public RestResponse endActiveLotteries() {
        lotteryService.endAllActiveLotteriesAndSelectWinners();
        return RestResponse.successBuild("All active lotteries ended successfully!");
    }

    @PostMapping("/endLottery/{lotteryId}")
    public RestResponse endLottery(@PathVariable("lotteryId") Long lotteryId) throws DataNotFoundException, LotteryStatusException {
        if (!isValidLotteryId(lotteryId)) {
            throw new DataNotFoundException("Lottery not found for id: " + lotteryId);
        }
        lotteryService.endLotteryAndSelectLotteryWinner(lotteryId);
        return RestResponse.successBuild("Lottery ended successfully!");
    }

    private boolean isValidLotteryId(Long lotteryId) {
        return !(lotteryId == null || lotteryId < 0);
    }

    @GetMapping(value = "/activeLotteries")
    public RestResponse getActiveLotteries() {
        return RestResponse.successBuild(lotteryService.getActiveLotteries());
    }

    @PostMapping(value = "/startLottery")
    public RestResponse startLottery(@RequestParam("lotteryName") String lotteryName) throws SaveFailureException {
        return RestResponse.successBuild(lotteryService.startLotteryByName(lotteryName));
    }

    @GetMapping(value = "/lotteryResult/{lotteryId}")
    public RestResponse getLotteryResultByLotteryIdAndDate(@PathVariable("lotteryId") Long lotteryId, @RequestParam("date") String date) throws LotteryStatusException, DataNotFoundException {
        if (!isValidLotteryId(lotteryId)) {
            throw new DataNotFoundException("Lottery not found for id: " + lotteryId);
        }
        return RestResponse.successBuild(lotteryService.getLotteryResultByLotteryIdAndDate(lotteryId, date));
    }
}
