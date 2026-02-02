package org.airtribe.LearnerManagementSystemBelC17.controller;

import java.util.List;
import org.airtribe.LearnerManagementSystemBelC17.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC17.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC17.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystemBelC17.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC17.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CohortController {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @PostMapping("/cohorts")
  public Cohort createCohort(@RequestBody Cohort cohort) {
    return _learnerManagementService.createCohort(cohort);
  }

  @PostMapping("/assignLearnerToCohort")
  public Cohort assignLearnersToCohort(@RequestParam("learnerId") Long learnerId,
      @RequestParam("cohortId") Long cohortId) throws CohortNotFoundException, LearnerNotFoundException {
    return _learnerManagementService.assignLearnerToCohort(learnerId, cohortId);
  }

  @GetMapping("/cohorts")
  public List<Cohort> getAllCohorts() {
    return _learnerManagementService.fetchAllCohorts();
  }

  @PostMapping("/cohorts/{cohortId}/learners")
  public Cohort assignAndCreateLearners(@PathVariable("cohortId") Long cohortId, @RequestBody List<Learner> learners)
      throws CohortNotFoundException {
    return _learnerManagementService.createAndAssignLearnersToCohort(cohortId, learners);
  }

  @ExceptionHandler(LearnerNotFoundException.class)
  public ResponseEntity<String> handleLearnerNotFoundException(LearnerNotFoundException exception) {
    return ResponseEntity.status(404).body(exception.getMessage());
  }

  @ExceptionHandler(CohortNotFoundException.class)
  public ResponseEntity<String> handleCohortNotFoundException(CohortNotFoundException exception) {
    return ResponseEntity.status(404).body(exception.getMessage());
  }
}

// Create a separate endpoint -> Creating the relationship
// cohortId , learnerId
// assignLeanerToCohort?learnerId=1&cohortId=2
// assignLeanerToCohort?learnerIds=1,2,3,4,5,6,7&cohortId=2
// assignLeanerToCohort?cohortId=1
//    body {cohortId:1, learnerIds: [1,2,3,4,5]}

// /cohorts/1/learners -> POST -> body -> [1,2,3,4,5]