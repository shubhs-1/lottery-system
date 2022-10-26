package com.assignment.lotterysystem.controller;

import com.assignment.lotterysystem.dto.RestResponse;
import com.assignment.lotterysystem.dto.ParticipantDto;
import com.assignment.lotterysystem.exception.UserAlreadyExistsException;
import com.assignment.lotterysystem.model.Participant;
import com.assignment.lotterysystem.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Shubham Kalaria
 * Controller to handle requests for participant related services
 */
@RestController
public class ParticipantController {

    @Autowired
    ParticipantService participantService;

    /**
     * API endpoint for participant to register
     * @param participantDto
     * @param errors
     * @return ResponseEntity
     * @throws UserAlreadyExistsException
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerParticipant(@Valid @RequestBody ParticipantDto participantDto, Errors errors) throws UserAlreadyExistsException {
        if(errors.hasErrors()) {
            return new ResponseEntity<>(RestResponse.failureBuild(errors.getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        Participant participant = participantService.registerParticipant(participantDto);
        return new ResponseEntity<>(RestResponse.successBuild(participant), HttpStatus.CREATED);
    }
}
