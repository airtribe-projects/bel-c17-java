package org.airtribe.LearnerManagementSystemBelC17.service;

import java.util.List;
import java.util.Optional;
import org.airtribe.LearnerManagementSystemBelC17.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC17.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC17.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystemBelC17.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC17.exception.ValidationFailedException;
import org.airtribe.LearnerManagementSystemBelC17.repository.CohortRepository;
import org.airtribe.LearnerManagementSystemBelC17.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LearnerManagementService {

  @Autowired
  private LearnerRepository _learnerRepository;

  @Autowired
  private CohortRepository _cohortRepository;

  public Learner createLearner(Learner learner)  {
    return _learnerRepository.save(learner);
  }

  public List<Learner> fetchAllLearners() {
    return _learnerRepository.findAll();
  }

  public Learner fetchById(Long learnerId) throws LearnerNotFoundException, ValidationFailedException {
    if (learnerId < 0) {
      throw new ValidationFailedException("Learner Id cannot be negative: " + learnerId);
    }
    if (_learnerRepository.findById(learnerId).isPresent()) {
      return _learnerRepository.findById(learnerId).get();
    }
   // Eating up an exception

    throw new LearnerNotFoundException("Learner not found with Id: " + learnerId);

  }

  public List<Learner> fetchLearnerByName(String learnerName) {
    return _learnerRepository.findByLearnerName(learnerName);
  }

  public List<Learner> fetchLearnerComplexParams(String learnerName, Long learnerId)
      throws LearnerNotFoundException, ValidationFailedException {
    if (learnerId != null) {
      return List.of(fetchById(learnerId));
    }
    if (learnerName != null) {
      return fetchLearnerByName(learnerName);
    }
    return fetchAllLearners();
  }

  public Cohort createCohort(Cohort cohort) {
    return _cohortRepository.save(cohort);
  }

  public Cohort assignLearnerToCohort(Long learnerId, Long cohortId)
      throws LearnerNotFoundException, CohortNotFoundException {
    Optional<Learner> learnerOptional = _learnerRepository.findById(learnerId);

    if (learnerOptional.isEmpty()) {
      throw new LearnerNotFoundException("Learner not found with Id: " + learnerId);
    }

    Optional<Cohort> cohortOptional = _cohortRepository.findById(cohortId);

    if (cohortOptional.isEmpty()) {
      throw new CohortNotFoundException("Cohort not found with Id: " + cohortId);
    }

    Cohort savedCohort = cohortOptional.get();
    Learner savedLearner = learnerOptional.get();

    List<Learner> enrolledLearners = savedCohort.getLearners();
    for (Learner learner : enrolledLearners) {
      if (learner.getLearnerId().equals(learnerId)) {
        // Learner is already assigned to the cohort
        return savedCohort;
      }
    }

    enrolledLearners.add(savedLearner);
    savedCohort.setLearners(enrolledLearners);
    return _cohortRepository.save(savedCohort);

  }

  public List<Cohort> fetchAllCohorts() {
    return _cohortRepository.findAll();
  }
}
