package com.assignment.lotterysystem.controller;

import com.assignment.lotterysystem.repository.LotteryBallotRepository;
import com.assignment.lotterysystem.repository.LotteryRepository;
import com.assignment.lotterysystem.repository.ParticipantRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LotteryBallotControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private LotteryBallotRepository lotteryBallotRepository;

    @Before
    public void initEach() throws Exception {
        MockitoAnnotations.openMocks( this );
        participantRepository.deleteAll();
        lotteryRepository.deleteAll();
        lotteryBallotRepository.deleteAll();
        RequestBuilder registerUserRequest = MockMvcRequestBuilders
                .post("/register")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"john_doe\",\"firstname\": \"John\",\"lastname\": \"Doe\"}")
                .contentType(MediaType.APPLICATION_JSON);
        RequestBuilder startLotteryRequest = MockMvcRequestBuilders
                .post("/lottery/startLottery")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("lotteryName", "LotteryA")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(registerUserRequest).andDo(request -> mockMvc.perform(startLotteryRequest));
    }

    @Test
    public void shouldSubmitLotterySuccessfully() throws Exception {
        RequestBuilder submitLotteryRequest = MockMvcRequestBuilders
                .post("/lotteryBallot/submitLottery/3")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("username", "john_doe")
                .contentType(MediaType.APPLICATION_JSON);

        String expectedData = "lotteryId\":3,\"username\":\"john_doe\",\"lotteryNumber\":1";
        String expectedHttpStatus = "200";

        MvcResult result = mockMvc.perform(submitLotteryRequest).andReturn();

        Assert.assertTrue(result.getResponse().getContentAsString().contains(expectedData));
        Assert.assertEquals(String.valueOf(result.getResponse().getStatus()), expectedHttpStatus);
    }

    @Test
    public void shouldThrowDataNotFoundExceptionWithCorrectMessageWhenLotteryIdNotFound() throws Exception {
        RequestBuilder submitLotteryRequest = MockMvcRequestBuilders
                .post("/lotteryBallot/submitLottery/44")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("username", "john_doe")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(submitLotteryRequest).andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
        String expectedHttpStatus = "404";

        Assert.assertEquals(String.valueOf(result.getResponse().getStatus()), expectedHttpStatus);
        Assert.assertTrue(jsonObject.get("messages").getAsJsonArray().get(0).getAsJsonObject().get("exception").getAsString().contains("DataNotFoundException"));
        Assert.assertEquals(jsonObject.get("messages").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsString(), "Lottery not found for id: 44");
    }

    @Test
    public void shouldThrowDataNotFoundExceptionWithCorrectMessageWhenUsernameNotFound() throws Exception {
        RequestBuilder submitLotteryRequest = MockMvcRequestBuilders
                .post("/lotteryBallot/submitLottery/1")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("username", "doejohn")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(submitLotteryRequest).andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
        String expectedHttpStatus = "404";

        Assert.assertEquals(String.valueOf(result.getResponse().getStatus()), expectedHttpStatus);
        Assert.assertTrue(jsonObject.get("messages").getAsJsonArray().get(0).getAsJsonObject().get("exception").getAsString().contains("DataNotFoundException"));
        Assert.assertEquals(jsonObject.get("messages").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsString(), "Username not found: doejohn");
    }
}
