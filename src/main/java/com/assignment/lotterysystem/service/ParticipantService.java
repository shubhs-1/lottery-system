package com.assignment.lotterysystem.service;

import com.assignment.lotterysystem.dto.ParticipantDto;
import com.assignment.lotterysystem.exception.UserAlreadyExistsException;
import com.assignment.lotterysystem.model.Participant;

public interface ParticipantService {
    Participant registerParticipant(ParticipantDto participantDto) throws UserAlreadyExistsException;

    Participant findParticipantByUsername(String username);
}
