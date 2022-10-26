package com.assignment.lotterysystem.service;

import com.assignment.lotterysystem.dto.ParticipantDto;
import com.assignment.lotterysystem.exception.UserAlreadyExistsException;
import com.assignment.lotterysystem.model.Participant;

/**
 * @author Shubham Kalaria
 * Interface to provide services related to Participant
 */
public interface ParticipantService {
    /**
     * Method to provide registration service for participants
     * @param participantDto
     * @return Participant
     * @throws UserAlreadyExistsException
     */
    Participant registerParticipant(ParticipantDto participantDto) throws UserAlreadyExistsException;

    /**
     * Method to find participant details by username
     * @param username
     * @return Participant
     */
    Participant findParticipantByUsername(String username);
}
