package org.airtribe.LearnerManagementSystemBelC17.integTests;

import java.util.ArrayList;
import java.util.List;
import org.airtribe.LearnerManagementSystemBelC17.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC17.entity.LearnerDTO;
import org.airtribe.LearnerManagementSystemBelC17.repository.LearnerRepository;
import org.hibernate.resource.beans.internal.BeansMessageLogger_$logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class LearnerManagementSystemIntegTests {
  @Autowired
  private MockMvc _mockMvc;

  @Autowired
  private LearnerRepository _learnerRepository;

  @AfterEach
  public void cleanup() {
    _learnerRepository.deleteAll();
  }


  @Test
  public void testCreateLearner() throws Exception {
    _mockMvc.perform(MockMvcRequestBuilders.post("/learners").content("{\"learnerName\":\"test\",\"learnerEmail\":\"test@gmail.com\",\"learnerPhone\":\"12345\"}")
            .contentType("application/json"))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void testFetchAllLearners() throws Exception {
    Learner savedLearner = _learnerRepository.save(new Learner("test", "test@gmail.com", "1234", new ArrayList<>()));
    _mockMvc.perform(MockMvcRequestBuilders.get("/learners")).andExpect(status().isOk()).andDo(print()).andExpect(
            jsonPath("$[0].learnerName").value("test")
        ).andExpect(jsonPath("$[0].learnerEmail").value("test@gmail.com"))
        .andExpect(jsonPath("$[0].learnerId").value(savedLearner.getLearnerId()))
        .andExpect(jsonPath("$", hasSize(1)));
  }

}
