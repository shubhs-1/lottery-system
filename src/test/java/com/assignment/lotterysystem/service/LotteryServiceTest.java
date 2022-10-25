package com.assignment.lotterysystem.service;

import com.assignment.lotterysystem.dto.LotteryDto;
import com.assignment.lotterysystem.exception.DataNotFoundException;
import com.assignment.lotterysystem.exception.LotteryStatusException;
import com.assignment.lotterysystem.exception.SaveFailureException;
import com.assignment.lotterysystem.model.Lottery;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryServiceTest {

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private LotteryBallotRepository lotteryBallotRepository;

    final static String LOTTERY_A = "LotteryAA";
    final static String LOTTERY_B = "LotteryBB";
    final static String DATE = LocalDate.now().toString();

    @Before
    public void initEach() {
        participantRepository.deleteAll();
        lotteryRepository.deleteAll();
        lotteryBallotRepository.deleteAll();
    }

    @Test
    public void shouldStartLottery() throws SaveFailureException {
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);

        Assert.assertNotNull(lottery);
        Assert.assertNotNull(lottery.getId());
        Assert.assertNotNull(lottery.getStartDate());
        Assert.assertNull(lottery.getEndDate());
        Assert.assertEquals(LOTTERY_A, lottery.getName());
    }

    @Test(expected = SaveFailureException.class)
    public void shouldThrowSaveFailureExceptionWhenActiveLotteryWithSameNameExist() throws SaveFailureException {
        lotteryService.startLotteryByName(LOTTERY_A);
        lotteryService.startLotteryByName(LOTTERY_A);
    }


    @Transactional
    @Test
    public void shouldFindLotteryById() throws DataNotFoundException, SaveFailureException {
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);

        Lottery lottery2 = lotteryService.findByLotteryId(lottery.getId());

        Assert.assertEquals(lottery, lottery2);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldThrowDataNotFoundExceptionWhenLotteryNotFound() throws DataNotFoundException {
        lotteryService.findByLotteryId(null);
    }

    @Test
    public void repositoryShouldBeEmptyWhenDeleteAllFromRepository() {
        lotteryRepository.deleteAll();
        Assert.assertEquals(0, lotteryRepository.count());
    }

    @Transactional
    @Test
    public void shouldGetAllActiveLotteries() throws SaveFailureException {
        List<Lottery> expectedLotteries = new ArrayList<>();
        expectedLotteries.add(lotteryService.startLotteryByName(LOTTERY_A));
        expectedLotteries.add(lotteryService.startLotteryByName(LOTTERY_B));

        List<Lottery> givenLotteries = lotteryService.getActiveLotteries();

        Assert.assertEquals(expectedLotteries, givenLotteries);
    }

    @Transactional
    @Test
    public void shouldGetActiveLotteriesAfterLotteryIsEnded() throws DataNotFoundException, SaveFailureException, LotteryStatusException {
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);
        Lottery lottery2 = lotteryService.startLotteryByName(LOTTERY_B);

        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());

        Assert.assertEquals(1, lotteryService.getActiveLotteries().size());
    }

    @Transactional
    @Test
    public void shouldEndLotteryById() throws DataNotFoundException, SaveFailureException, LotteryStatusException {
        final String lotteryName = UUID.randomUUID().toString();
        Lottery lottery = lotteryService.startLotteryByName(lotteryName);

        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());

        Assert.assertNotNull(lottery.getEndDate());
    }

    @Test(expected = LotteryStatusException.class)
    public void shouldThrowLotteryStatusExceptionWhenLotteryIsFinished() throws DataNotFoundException, SaveFailureException, LotteryStatusException {
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);
        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());

        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());
    }

    @Transactional
    @Test
    public void shouldEndActiveLotteries() throws SaveFailureException {
        lotteryService.startLotteryByName(LOTTERY_A);
        lotteryService.startLotteryByName(LOTTERY_B);

        lotteryService.endAllActiveLotteriesAndSelectWinners();

        Assert.assertEquals(0, lotteryService.getActiveLotteries().size());
    }

    @Transactional
    @Test
    public void shouldEndOtherActiveLotteriesWhenOneOfThemFails() throws DataNotFoundException, SaveFailureException, LotteryStatusException {
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);
        lotteryService.startLotteryByName(LOTTERY_B);
        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());

        lotteryService.endAllActiveLotteriesAndSelectWinners();

        Assert.assertEquals(0, lotteryService.getActiveLotteries().size());
    }

    @Transactional
    @Test
    public void shouldSaveLotteryResult() throws SaveFailureException, DataNotFoundException, LotteryStatusException {
        Lottery lottery = createAndEndLottery();

        Assert.assertNotNull(lottery.getEndDate());
        Assert.assertNotNull(lottery.getLotteryWinner());
    }


    @Test
    public void shouldGiveLotteryResultWhenLotteryIdIsValid() throws DataNotFoundException, SaveFailureException, LotteryStatusException {
        Lottery lottery = createAndEndLottery();
        LotteryDto result = lotteryService.getLotteryResultByLotteryIdAndDate(lottery.getId(), DATE);

        Assert.assertNotNull(result);
    }

    @Transactional
    @Test(expected = DataNotFoundException.class)
    public void shouldThrowDataNotFoundExceptionWhenLotteryIdIsInvalid() throws DataNotFoundException, LotteryStatusException {
        lotteryService.getLotteryResultByLotteryIdAndDate(null, null);
    }

    @Transactional
    @Test(expected = LotteryStatusException.class)
    public void shouldThrowLotteryStatusExceptionWhenLotteryIsStillActive() throws DataNotFoundException, SaveFailureException, LotteryStatusException {
        final String lotteryName = UUID.randomUUID().toString();
        Lottery lottery = lotteryService.startLotteryByName(lotteryName);
        lotteryService.getLotteryResultByLotteryIdAndDate(lottery.getId(), DATE);
    }

    private Lottery createAndEndLottery() throws LotteryStatusException, DataNotFoundException, SaveFailureException {
        final String lotteryName = UUID.randomUUID().toString();
        Lottery lottery = lotteryService.startLotteryByName(lotteryName);
        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());
        return lottery;
    }
}
