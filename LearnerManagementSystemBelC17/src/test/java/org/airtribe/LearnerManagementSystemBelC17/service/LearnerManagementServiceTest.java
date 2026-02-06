package org.airtribe.LearnerManagementSystemBelC17.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.airtribe.LearnerManagementSystemBelC17.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC17.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC17.exception.ValidationFailedException;
import org.airtribe.LearnerManagementSystemBelC17.repository.LearnerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;


@SpringBootTest
public class LearnerManagementServiceTest {

  @InjectMocks
  private LearnerManagementService _learnerManagementService;

  @Mock
  private LearnerRepository _learnerRepository;

  private Learner learner;

  @BeforeAll
  public static void setup() {
    System.out.println("Setting up LearnerManagementServiceTest");
  }

  @BeforeEach
  public void beforeEachTest() {
     learner = new Learner(1L, "test", "test", "test");
    System.out.println("Starting a test method in LearnerManagementServiceTest");
  }

  @AfterEach
  public void afterEachTest() {
    System.out.println("Finished a test method in LearnerManagementServiceTest");
  }

  @AfterAll
  public static void tearDown() {
    System.out.println("All tests in LearnerManagementServiceTest are done");
  }


  @Test
  public void testCreateLearner() {
    // ARRANGE
    when(_learnerRepository.save(learner)).thenReturn(learner);
    // ACT
    Learner returnedLearner = _learnerManagementService.createLearner(learner);
    // ASSERT
    Assertions.assertEquals(learner, returnedLearner);
    Assertions.assertEquals(1L, returnedLearner.getLearnerId());
    Assertions.assertEquals("test", returnedLearner.getLearnerName());
    Assertions.assertEquals("test", returnedLearner.getLearnerEmail());
    verify(_learnerRepository, times(1)).save(learner);
  }

  @Test
  public void testFetchLearnerByIdSuccessfully() throws ValidationFailedException, LearnerNotFoundException {
    // arrange
    when(_learnerRepository.findById(1L)).thenReturn(java.util.Optional.of(learner));

    // act
    Learner fetchedLearner = _learnerManagementService.fetchById(1L);

    // asserion
    Assertions.assertEquals(learner, fetchedLearner);
    Assertions.assertEquals(1L, fetchedLearner.getLearnerId());
    Assertions.assertEquals("test", fetchedLearner.getLearnerName());
    Assertions.assertEquals("test", fetchedLearner.getLearnerEmail());
    verify(_learnerRepository, times(1)).findById(1L);
  }

  @Test
  public void testFetchLearnerById_ThrowsLearnerNotFoundException() throws ValidationFailedException {
    when(_learnerRepository.findById(1L)).thenReturn(java.util.Optional.empty());
    Assertions.assertThrows(LearnerNotFoundException.class, () -> {
      _learnerManagementService.fetchById(1L);
    }, "Learner not found with Id: 1");
  }

  @Test
  public void testFetchLearnerById_ThrowsValidationFailedException() {
    Assertions.assertThrows(ValidationFailedException.class, () -> {
      _learnerManagementService.fetchById(-1L);
    }, "Learner Id cannot be negative: -1");
  }
}
