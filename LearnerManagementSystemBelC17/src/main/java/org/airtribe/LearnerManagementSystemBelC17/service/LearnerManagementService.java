package org.airtribe.LearnerManagementSystemBelC17.service;

import java.util.List;
import org.airtribe.LearnerManagementSystemBelC17.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC17.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LearnerManagementService {

  @Autowired
  private LearnerRepository _learnerRepository;

  public Learner createLearner(Learner learner)  {
    return _learnerRepository.save(learner);
  }

  public List<Learner> fetchAllLearners() {
    return _learnerRepository.findAll();
  }
}
