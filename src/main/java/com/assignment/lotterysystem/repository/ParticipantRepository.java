package com.assignment.lotterysystem.repository;

import com.assignment.lotterysystem.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Participant findByUsername(String username);
}
