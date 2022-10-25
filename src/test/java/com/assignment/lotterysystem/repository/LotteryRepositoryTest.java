package com.assignment.lotterysystem.repository;

import com.assignment.lotterysystem.model.Lottery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryRepositoryTest {

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
    public void shouldFindActiveLotteriesAfterDataIsSaved() {
        Lottery lottery = new Lottery();
        lottery.setStartDate(LocalDate.now());
        lottery.setName("test");
        List<Lottery> expected = Arrays.asList(lottery);

        lotteryRepository.save(lottery);

        List<Lottery> actual = lotteryRepository.findLotteriesByEndDateIsNull();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldFindByLotteryIdAfterDataIsSaved() {
        Lottery lottery = new Lottery();
        lottery.setStartDate(LocalDate.now());
        lottery.setName("test");

        lotteryRepository.save(lottery);

        Lottery actual = lotteryRepository.findById(lottery.getId());
        Assert.assertEquals(lottery, actual);
    }

    @Test
    public void shouldCountActiveLotteriesByNameAfterDataIsSaved() {
        Lottery lottery = new Lottery();
        lottery.setStartDate(LocalDate.now());
        lottery.setName("test");

        Lottery lottery2 = new Lottery();
        lottery2.setStartDate(LocalDate.parse("2022-10-23"));
        lottery2.setEndDate(LocalDate.parse(("2022-10-24")));
        lottery2.setName("test2");

        lotteryRepository.save(lottery);
        lotteryRepository.save(lottery2);

        Long actual = lotteryRepository.countByNameAndEndDateIsNull(lottery.getName());
        Long expected = 1L;
        Assert.assertEquals(expected, actual);
    }
}
