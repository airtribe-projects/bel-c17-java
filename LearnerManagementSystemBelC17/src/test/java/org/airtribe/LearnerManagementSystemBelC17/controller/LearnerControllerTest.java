package org.airtribe.LearnerManagementSystemBelC17.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.airtribe.LearnerManagementSystemBelC17.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC17.entity.LearnerDTO;
import org.airtribe.LearnerManagementSystemBelC17.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC17.exception.ValidationFailedException;
import org.airtribe.LearnerManagementSystemBelC17.service.LearnerManagementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class LearnerControllerTest {

  @MockitoBean
  private LearnerManagementService _learnerManagementService;

  @Autowired
  private MockMvc _mockMvc;

  @Test
  public void testFetchAllLearners() throws Exception {
    List<Learner> learnerList = new ArrayList<>();
    List<LearnerDTO> learnerDTOS = new ArrayList<>();
    learnerDTOS.add(new LearnerDTO(1L, "test", "test", "test", new ArrayList<>()));
    learnerList.add(new Learner(1L, "test", "test", "test", new ArrayList<>()));
    when(_learnerManagementService.fetchLearnerComplexParams(null, null)).thenReturn(learnerList);
    _mockMvc.perform(MockMvcRequestBuilders.get("/learners")).andExpect(status().isOk()).andDo(print()).andExpect(
        jsonPath("$[0].learnerName").value("test")
    ).andExpect(jsonPath("$[0].learnerEmail").value("test"))
        .andExpect(jsonPath("$[0].learnerId").value(1L));
  }

  private static Stream<Arguments> invalidLearnerInputProvider() {
    return Stream.of(
        Arguments.of(
            "{\"learnerEmail\":\"test@test.com\",\"learnerPhone\":\"1234567890\"}",
            "Missing learnerName should fail validation"),
        Arguments.of(
            "{\"learnerName\":null,\"learnerEmail\":\"test@test.com\",\"learnerPhone\":\"1234567890\"}",
            "Null learnerName should fail validation"),
        Arguments.of(
            "{\"learnerName\":\"test\",\"learnerPhone\":\"1234567890\"}",
            "Missing learnerEmail should fail validation"),
        Arguments.of(
            "{\"learnerName\":\"test\",\"learnerEmail\":null,\"learnerPhone\":\"1234567890\"}",
            "Null learnerEmail should fail validation"),
        Arguments.of(
            "{\"learnerName\":\"test\",\"learnerEmail\":\"\",\"learnerPhone\":\"1234567890\"}",
            "Empty learnerEmail should fail validation"),
        Arguments.of(
            "{\"learnerName\":\"test\",\"learnerEmail\":\"notanemail\",\"learnerPhone\":\"1234567890\"}",
            "Invalid email format should fail validation"),
        Arguments.of(
            "{\"learnerName\":\"test\",\"learnerEmail\":\"test@test.com\"}",
            "Missing learnerPhone should fail validation"),
        Arguments.of(
            "{\"learnerName\":\"test\",\"learnerEmail\":\"test@test.com\",\"learnerPhone\":null}",
            "Null learnerPhone should fail validation"),
        Arguments.of(
            "{\"learnerName\":\"test\",\"learnerEmail\":\"test@test.com\",\"learnerPhone\":\"\"}",
            "Empty learnerPhone should fail validation"),
        Arguments.of(
            "{}",
            "All fields missing should fail validation")
    );
  }

  @ParameterizedTest(name = "{1}")
  @MethodSource("invalidLearnerInputProvider")
  public void testCreateLearner_ValidationFails(String requestBody, String testDescription) throws Exception {
    _mockMvc.perform(MockMvcRequestBuilders.post("/learners")
            .content(requestBody)
            .contentType("application/json"))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }








}
