package org.airtribe.LearnerManagementSystemBelC17.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.airtribe.LearnerManagementSystemBelC17.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC17.entity.CohortDTO;
import org.airtribe.LearnerManagementSystemBelC17.entity.Course;
import org.airtribe.LearnerManagementSystemBelC17.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC17.entity.LearnerDTO;
import org.airtribe.LearnerManagementSystemBelC17.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystemBelC17.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC17.exception.ValidationFailedException;
import org.airtribe.LearnerManagementSystemBelC17.repository.CohortRepository;
import org.airtribe.LearnerManagementSystemBelC17.repository.CourseRepository;
import org.airtribe.LearnerManagementSystemBelC17.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LearnerManagementService {

  @Autowired
  private LearnerRepository _learnerRepository;

  @Autowired
  private CohortRepository _cohortRepository;

  @Autowired
  private CourseRepository _courseRepository;

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

    Optional<Learner> learnerOptional = _learnerRepository.findById(learnerId);

    if (learnerOptional.isPresent()) {
      return learnerOptional.get();
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

  public List<LearnerDTO> convertToLearnerDTO(List<Learner> learners) {
    List<LearnerDTO> learnerDTOS = new ArrayList<>();
    for (Learner learner : learners) {
      LearnerDTO learnerDTO = new LearnerDTO();
      learnerDTO.setLearnerId(learner.getLearnerId());
      learnerDTO.setLearnerEmail(learner.getLearnerEmail());
      learnerDTO.setLearnerName(learner.getLearnerName());
      learnerDTO.setLearnerPhone(learner.getLearnerPhone());
      List<CohortDTO> cohortDTOS  = new ArrayList<>();
      for (Cohort cohort: learner.getCohorts())  {
        CohortDTO cohortDTO = new CohortDTO();
        cohortDTO.setCohortId(cohort.getCohortId());
        cohortDTO.setCohortName(cohort.getCohortName());
        cohortDTO.setCohortDescription(cohort.getCohortDescription());
        cohortDTOS.add(cohortDTO);
      }
      learnerDTO.setCohorts(cohortDTOS);
      learnerDTOS.add(learnerDTO);
    }

    return learnerDTOS;
  }

  public Course createCourse(Course course) {
    return _courseRepository.save(course);
  }

  @Transactional
  public Cohort createAndAssignLearnersToCohort(Long cohortId, List<Learner> learners) throws CohortNotFoundException {
    Optional<Cohort> cohortOptional = _cohortRepository.findById(cohortId);
    if (cohortOptional.isEmpty()) {
      throw new CohortNotFoundException("Cohort not found with Id: " + cohortId);
    }

    Cohort fetchecCohort = cohortOptional.get();
//    List<Learner> managedLearners = new ArrayList<>();
//    for (Learner learner: learners) {
//      Optional<Learner> savedLearner = _learnerRepository.findByLearnerEmail(learner.getLearnerEmail());
//      if (savedLearner.isEmpty()) {
//        managedLearners.add(_learnerRepository.save(learner));
//      } else {
//        managedLearners.add(savedLearner.get());
//      }
//    }
//
//    fetchecCohort.getLearners().addAll(managedLearners);
    fetchecCohort.getLearners().addAll(learners);
    return _cohortRepository.save(fetchecCohort);
  }

  public Page<Cohort> fetchPaginatedAndSortedCohorts(int pageSize, int pageNumber, String sortBy, String sortDir) {
    Sort.Direction direction;
    if (sortDir.equalsIgnoreCase("asc")) {
      direction = Sort.Direction.ASC;
    } else {
      direction = Sort.Direction.DESC;
    }

    Pageable pageable = PageRequest.of(pageNumber, pageSize, direction, sortBy);
    return _cohortRepository.findAll(pageable);
  }
}

// assignCohortToCourse?