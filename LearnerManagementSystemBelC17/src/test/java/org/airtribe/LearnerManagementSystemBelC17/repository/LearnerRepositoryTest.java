package org.airtribe.LearnerManagementSystemBelC17.repository;

import java.util.List;
import java.util.Optional;
import org.airtribe.LearnerManagementSystemBelC17.entity.Learner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LearnerRepositoryTest {

    @Autowired
    private LearnerRepository learnerRepository;

    private Learner learner;

    @BeforeEach
    void setup() {
        learner = new Learner(null, "Alice", "alice@example.com", "9876543210");
    }

    @Test
    void saveLearner_persistsAndAssignsId() {
        // Arrange: learner prepared in setup()

        // Act
        Learner saved = learnerRepository.save(learner);

        // Assert
        assertNotNull(saved.getLearnerId());
        assertEquals("Alice", saved.getLearnerName());
        assertEquals("alice@example.com", saved.getLearnerEmail());
    }

    @Test
    void findById_returnsLearner_whenExists() {
        // Arrange
        Learner saved = learnerRepository.save(learner);

        // Act
        Optional<Learner> result = learnerRepository.findById(saved.getLearnerId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(saved.getLearnerId(), result.get().getLearnerId());
    }

    @Test
    void findById_returnsEmpty_whenNotExists() {
        // Act
        Optional<Learner> result = learnerRepository.findById(999L);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_returnsAllSavedLearners() {
        // Arrange
        learnerRepository.save(learner);
        learnerRepository.save(new Learner(null, "Bob", "bob@example.com", "1234567890"));

        // Act
        List<Learner> all = learnerRepository.findAll();

        // Assert
        assertEquals(2, all.size());
    }

    @Test
    void findByLearnerName_returnsMatchingLearners() {
        // Arrange
        learnerRepository.save(learner);

        // Act
        List<Learner> result = learnerRepository.findByLearnerName("Alice");

        // Assert
        assertEquals(1, result.size());
        assertEquals("alice@example.com", result.get(0).getLearnerEmail());
    }

    @Test
    void findByLearnerName_returnsEmpty_whenNoMatch() {
        // Arrange
        learnerRepository.save(learner);

        // Act
        List<Learner> result = learnerRepository.findByLearnerName("Unknown");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findByMyName_returnsMatchingLearners() {
        // Arrange
        learnerRepository.save(learner);

        // Act
        List<Learner> result = learnerRepository.findByMyName("Alice");

        // Assert
        assertEquals(1, result.size());
        assertEquals("alice@example.com", result.get(0).getLearnerEmail());
    }

    @Test
    void findByLearnerEmail_returnsLearner_whenExists() {
        // Arrange
        learnerRepository.save(learner);

        // Act
        Optional<Learner> result = learnerRepository.findByLearnerEmail("alice@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getLearnerName());
    }

    @Test
    void findByLearnerEmail_returnsEmpty_whenNotExists() {
        // Act
        Optional<Learner> result = learnerRepository.findByLearnerEmail("none@example.com");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteById_removesLearner() {
        // Arrange
        Learner saved = learnerRepository.save(learner);

        // Act
        learnerRepository.deleteById(saved.getLearnerId());

        // Assert
        assertFalse(learnerRepository.findById(saved.getLearnerId()).isPresent());
    }
}
