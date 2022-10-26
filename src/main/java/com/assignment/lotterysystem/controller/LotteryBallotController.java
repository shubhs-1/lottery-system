package com.assignment.lotterysystem.controller;

import com.assignment.lotterysystem.dto.RestResponse;
import com.assignment.lotterysystem.exception.DataNotFoundException;
import com.assignment.lotterysystem.exception.LotteryStatusException;
import com.assignment.lotterysystem.exception.LotterySubmissionFailed;
import com.assignment.lotterysystem.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Shubham Kalaria
 * Controller to handle requests for Ballot related services
 */
@RestController
@RequestMapping("/lotteryBallot")
public class LotteryBallotController {
    @Autowired
    private LotteryService lotteryService;

    /**
     * API endpoint to submit lottery ballot for given username
     * @param lotteryId
     * @param username
     * @return RestResponse
     * @throws DataNotFoundException
     * @throws LotteryStatusException
     */
    @PostMapping(value = "/submitLottery/{lotteryId}")
    public RestResponse submitLottery(@PathVariable("lotteryId") Long lotteryId, @RequestParam("username") String username) throws DataNotFoundException, LotteryStatusException {
        return RestResponse.successBuild(lotteryService.submitLotteryBallotSync(lotteryId, username));
    }
}
