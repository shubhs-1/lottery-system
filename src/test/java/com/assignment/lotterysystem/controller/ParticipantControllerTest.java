package com.assignment.lotterysystem.controller;

import com.assignment.lotterysystem.dto.ParticipantDto;
import com.assignment.lotterysystem.repository.LotteryBallotRepository;
import com.assignment.lotterysystem.repository.LotteryRepository;
import com.assignment.lotterysystem.repository.ParticipantRepository;
import com.assignment.lotterysystem.service.ParticipantService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ParticipantControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private LotteryBallotRepository lotteryBallotRepository;

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @Before
    public void initEach() {
        MockitoAnnotations.openMocks( this );
        participantRepository.deleteAll();
        lotteryRepository.deleteAll();
        lotteryBallotRepository.deleteAll();
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @After
    public void close() {
        validatorFactory.close();
    }

    @Test
    public void shouldGiveCorrectMessageForConstraintViolationOfLastnameProperty() {
        ParticipantDto participant = new ParticipantDto();
        participant.setUsername("username");
        participant.setFirstname("firstname");
        participant.setLastname(null);

        Set<ConstraintViolation<ParticipantDto>> violations = validator.validate(participant);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.stream().findFirst().get().getMessage(), "Lastname cannot be null");
    }

    @Test
    public void shouldGiveCorrectMessageForConstraintViolationOfFirstnameProperty() {
        ParticipantDto participant = new ParticipantDto();
        participant.setUsername("username");
        participant.setFirstname("");
        participant.setLastname("lastname");

        Set<ConstraintViolation<ParticipantDto>> violations = validator.validate(participant);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.stream().findFirst().get().getMessage(), "Firstname cannot be null or empty");
    }

    @Test
    public void shouldGiveCorrectMessageForConstraintViolationOfUsernameProperty() {
        ParticipantDto participant = new ParticipantDto();
        participant.setUsername("");
        participant.setFirstname("firstname");
        participant.setLastname("lastname");

        Set<ConstraintViolation<ParticipantDto>> violations = validator.validate(participant);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.stream().findFirst().get().getMessage(), "Username cannot be null or empty");
    }

    @Test
    public void shouldRegisterParticipantSuccessfully() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"johndoe11\",\"firstname\": \"John\",\"lastname\": \"Doe\"}")
                .contentType(MediaType.APPLICATION_JSON);

        String expectedData = "\"username\":\"johndoe11\",\"firstname\":\"John\",\"lastname\":\"Doe";
        String expectedHttpStatus = "201";

        MvcResult result = mockMvc.perform(request).andReturn();

        Assert.assertTrue(result.getResponse().getContentAsString().contains(expectedData));
        Assert.assertEquals(String.valueOf(result.getResponse().getStatus()), expectedHttpStatus);
    }

    @Test
    public void shouldGiveUserAlreadyExistsExceptionWhenRegisteringSameParticipantAgain() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"johndoe11\",\"firstname\": \"John\",\"lastname\": \"Doe\"}")
                .contentType(MediaType.APPLICATION_JSON);

        String expectedHttpStatus = "409";

        mockMvc.perform(request);
        MvcResult result = mockMvc.perform(request).andReturn();

        Assert.assertTrue(result.getResponse().getContentAsString().contains("UserAlreadyExistsException"));
        Assert.assertEquals(String.valueOf(result.getResponse().getStatus()), expectedHttpStatus);
    }
}
