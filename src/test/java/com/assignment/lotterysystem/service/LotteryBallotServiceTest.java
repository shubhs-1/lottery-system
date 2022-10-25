package com.assignment.lotterysystem.service;

import com.assignment.lotterysystem.dto.ParticipantDto;
import com.assignment.lotterysystem.exception.DataNotFoundException;
import com.assignment.lotterysystem.exception.LotteryStatusException;
import com.assignment.lotterysystem.exception.SaveFailureException;
import com.assignment.lotterysystem.exception.UserAlreadyExistsException;
import com.assignment.lotterysystem.model.Lottery;
import com.assignment.lotterysystem.model.LotteryBallot;
import com.assignment.lotterysystem.repository.LotteryBallotRepository;
import com.assignment.lotterysystem.repository.LotteryRepository;
import com.assignment.lotterysystem.repository.ParticipantRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryBallotServiceTest {

    @Autowired
    private LotteryBallotService lotteryBallotService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private LotteryBallotRepository lotteryBallotRepository;

    private final static int NUM_THREADS = 100;

    @Before
    public void initEach() {
        participantRepository.deleteAll();
        lotteryRepository.deleteAll();
        lotteryBallotRepository.deleteAll();
    }

    @Test
    public void shouldSubmitLotteryBallot() throws SaveFailureException, DataNotFoundException, LotteryStatusException, UserAlreadyExistsException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        createUser("doejohn");

        LotteryBallot lotteryBallot = lotteryService.submitLotteryBallot(lottery.getId(), "doejohn");
        assertNotNull(lotteryBallot);
        assertNotNull(lotteryBallot.getId());
    }

    @Test
    public void shouldSubmitManyLotteryBallot() throws SaveFailureException, DataNotFoundException, LotteryStatusException, UserAlreadyExistsException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        createUser("doejohn");

        LotteryBallot lotteryBallot = lotteryService.submitLotteryBallot(lottery.getId(), "doejohn");
        LotteryBallot lotteryBallot2 = lotteryService.submitLotteryBallot(lottery.getId(), "doejohn");

        assertNotNull(lotteryBallot);
        assertNotNull(lotteryBallot.getId());
        assertNotNull(lotteryBallot2);
        assertNotNull(lotteryBallot2.getId());
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldThrowDataNotFoundExceptionWhenUsernameIsInvalid() throws SaveFailureException, DataNotFoundException, LotteryStatusException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        lotteryService.submitLotteryBallot(lottery.getId(), null);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldThrowDataNotFoundExceptionWhenUsernameIsNotRegistered() throws SaveFailureException, DataNotFoundException, LotteryStatusException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        lotteryService.submitLotteryBallot(lottery.getId(), "test");
    }

    @Test(expected = LotteryStatusException.class)
    public void shouldThrowLotteryStatusExceptionWhenLotteryIsFinished() throws SaveFailureException, DataNotFoundException, LotteryStatusException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());

        lotteryService.submitLotteryBallot(lottery.getId(), null);
    }

    @Test
    public void shouldSubmitLotteryBallotGivenMultiThreadedEnvironment() throws SaveFailureException, InterruptedException, UserAlreadyExistsException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        createUser("doejohn");

        generateMultiThreadEnvironment(lottery.getId());

        Assert.assertEquals(lotteryBallotRepository.count(), NUM_THREADS);
    }

    private void generateMultiThreadEnvironment(Long lotteryId) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                try {
                    lotteryService.submitLotteryBallot(lotteryId, "doejohn");
                } catch (DataNotFoundException | LotteryStatusException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void shouldSaveLotteryResultWhenLotteryWinnerIsSelected() throws SaveFailureException, DataNotFoundException, InterruptedException, UserAlreadyExistsException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        createUser("doejohn");

        generateMultiThreadEnvironment(lottery.getId());
        Long winnerNum = lotteryBallotService.selectRandomLotteryWinner(lottery.getId());

        assertNotNull(winnerNum);
    }

    @Test
    public void shouldSaveLotteryResultWhenLotteryWinnerDoesNotExist() throws SaveFailureException, DataNotFoundException, UserAlreadyExistsException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        createUser("doejohn");

        Long winnerNum = lotteryBallotService.selectRandomLotteryWinner(lottery.getId());
        Long expected = -1L;
        Assert.assertEquals(expected, winnerNum);
    }

    private void createUser(final String username) throws UserAlreadyExistsException {
        final ParticipantDto userDto = new ParticipantDto();
        userDto.setUsername(username);
        userDto.setFirstname("John");
        userDto.setLastname("Doe");
        participantService.registerParticipant(userDto);
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
