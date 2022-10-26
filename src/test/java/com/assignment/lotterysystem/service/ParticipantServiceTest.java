package com.assignment.lotterysystem.service;

import com.assignment.lotterysystem.dto.ParticipantDto;
import com.assignment.lotterysystem.exception.UserAlreadyExistsException;
import com.assignment.lotterysystem.model.Participant;
import com.assignment.lotterysystem.repository.LotteryBallotRepository;
import com.assignment.lotterysystem.repository.LotteryRepository;
import com.assignment.lotterysystem.repository.ParticipantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParticipantServiceTest {

    @Autowired
    private ParticipantService participantService;

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
    public void shouldSuccessfullyRegisterNewParticipant() throws UserAlreadyExistsException {
        String username = UUID.randomUUID().toString();
        ParticipantDto participantDto = createUserDto(username);

        Participant participant = participantService.registerParticipant(participantDto);

        assertNotNull(participant);
        assertNotNull(participant.getUsername());
        assertEquals(username, participant.getUsername());
        assertNotNull(participant.getId());
    }

    @Transactional
    @Test
    public void shouldFindPartipantByUsernameAfterBeingNewlyRegistered() throws UserAlreadyExistsException {
        Participant user = registerUser();
        Participant user2 = participantService.findParticipantByUsername(user.getUsername());
        assertEquals(user, user2);
    }


    private ParticipantDto createUserDto(final String username) {
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setUsername(username);
        participantDto.setFirstname("john1");
        participantDto.setLastname("doe1");
        return participantDto;
    }

    private Participant registerUser() throws UserAlreadyExistsException {
        String username = UUID.randomUUID().toString();
        ParticipantDto userDto = createUserDto(username);
        Participant user = participantService.registerParticipant(userDto);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(username, user.getUsername());
        return user;
    }
}
