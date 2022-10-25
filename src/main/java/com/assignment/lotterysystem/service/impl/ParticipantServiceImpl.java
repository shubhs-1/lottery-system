package com.assignment.lotterysystem.service.impl;

import com.assignment.lotterysystem.dto.ParticipantDto;
import com.assignment.lotterysystem.exception.UserAlreadyExistsException;
import com.assignment.lotterysystem.model.Participant;
import com.assignment.lotterysystem.repository.ParticipantRepository;
import com.assignment.lotterysystem.service.ParticipantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Log4j2
@Service
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    ParticipantRepository participantRepository;

    @Override
    public Participant registerParticipant(ParticipantDto participantDto) throws UserAlreadyExistsException {
        Participant existingParticipant = findParticipantByUsername(participantDto.getUsername());
        if(Objects.nonNull(existingParticipant) && participantDto.getUsername().equals(existingParticipant.getUsername())){
            throw new UserAlreadyExistsException("Username already exists");
        }
        Participant newParticipant = new Participant();
        newParticipant.setUsername(participantDto.getUsername());
        newParticipant.setFirstname(participantDto.getFirstname());
        newParticipant.setLastname(participantDto.getLastname());
        return participantRepository.save(newParticipant);
    }

    @Override
    public Participant findParticipantByUsername(String username) {
        return participantRepository.findByUsername(username);
    }
}
