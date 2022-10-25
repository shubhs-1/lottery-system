package com.assignment.lotterysystem.repository;

import com.assignment.lotterysystem.model.Participant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ParticipantRepositoryTest {

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
    public void shouldFindByUsernameAfterDataIsSaved() {
        Participant participant = new Participant();
        participant.setUsername("johndoe33");
        participantRepository.save(participant);

        Participant expected = participantRepository.findByUsername(participant.getUsername());

        Assert.assertEquals(participant, expected);
    }

}
