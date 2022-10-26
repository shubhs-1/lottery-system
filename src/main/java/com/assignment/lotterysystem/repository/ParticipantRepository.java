package com.assignment.lotterysystem.repository;

import com.assignment.lotterysystem.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Shubham Kalaria
 * JPA repository interface to declare methods for database layer communication
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    /**
     * Method to find participant by username
     * @param username
     * @return Participant
     */
    Participant findByUsername(String username);
}
