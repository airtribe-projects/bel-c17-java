package org.airtribe.LearnerManagementSystemBelC17.controller;

import java.util.List;
import org.airtribe.LearnerManagementSystemBelC17.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC17.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LearnerController {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @PostMapping("/learners")
  public Learner createLearner(@RequestBody Learner learner) {
    return _learnerManagementService.createLearner(learner);
  }

  @GetMapping("/learners")
  public List<Learner> fetchAllLearners() {
    return _learnerManagementService.fetchAllLearners();
  }
}