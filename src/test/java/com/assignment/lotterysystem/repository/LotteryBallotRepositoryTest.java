package com.assignment.lotterysystem.repository;

import com.assignment.lotterysystem.model.LotteryBallot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryBallotRepositoryTest {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private LotteryBallotRepository lotteryBallotRepository;

    @Before
    public void initEach() {
        participantRepository.deleteAll();
        lotteryRepository.deleteAll();
        lotteryBallotRepository.deleteAll();
    }

    @Test
    public void shouldFindByLotteryNumberAndLotteryIdAfterDataIsSaved() {
        LotteryBallot lotteryBallot = new LotteryBallot();
        lotteryBallot.setUsername("johndoe22");
        lotteryBallot.setLotteryId(1L);
        lotteryBallot.setLotteryNumber(5L);
        lotteryBallot.setDate(LocalDate.now());

        lotteryBallotRepository.save(lotteryBallot);

        LotteryBallot actual = lotteryBallotRepository.findByLotteryNumberAndLotteryId(lotteryBallot.getLotteryNumber(), lotteryBallot.getLotteryId());
        Assert.assertEquals(lotteryBallot, actual);
    }

    @Test
    public void shouldCountsByLotteryIdAfterDataIsSaved() {
        LotteryBallot lotteryBallot = new LotteryBallot();
        lotteryBallot.setUsername("johndoe22");
        lotteryBallot.setLotteryId(1L);
        lotteryBallot.setLotteryNumber(5L);
        lotteryBallot.setDate(LocalDate.now());

        LotteryBallot lotteryBallot2 = new LotteryBallot();
        lotteryBallot2.setUsername("johndoe22");
        lotteryBallot2.setLotteryId(2L);
        lotteryBallot2.setLotteryNumber(4L);
        lotteryBallot2.setDate(LocalDate.now());

        lotteryBallotRepository.save(lotteryBallot);
        lotteryBallotRepository.save(lotteryBallot2);

        Long expected = lotteryBallotRepository.countLotteryBallotByLotteryId(lotteryBallot.getLotteryId());
        Long actual = 1L;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldFindFirstByLotteryIdOrderByLotteryNumberDescAfterDataIsSaved() {
        LotteryBallot lotteryBallot = new LotteryBallot();
        lotteryBallot.setUsername("johndoe22");
        lotteryBallot.setLotteryId(1L);
        lotteryBallot.setLotteryNumber(4L);
        lotteryBallot.setDate(LocalDate.now());

        LotteryBallot lotteryBallot2 = new LotteryBallot();
        lotteryBallot2.setUsername("shubham");
        lotteryBallot2.setLotteryId(1L);
        lotteryBallot2.setLotteryNumber(5L);
        lotteryBallot2.setDate(LocalDate.now());

        LotteryBallot lotteryBallot3 = new LotteryBallot();
        lotteryBallot3.setUsername("shubham");
        lotteryBallot3.setLotteryId(1L);
        lotteryBallot3.setLotteryNumber(6L);
        lotteryBallot3.setDate(LocalDate.now());

        lotteryBallotRepository.save(lotteryBallot);
        lotteryBallotRepository.save(lotteryBallot2);
        lotteryBallotRepository.save(lotteryBallot3);

        LotteryBallot actual = lotteryBallotRepository.findFirstByLotteryIdOrderByLotteryNumberDesc(1L);

        Assert.assertEquals(lotteryBallot3, actual);
    }
}

