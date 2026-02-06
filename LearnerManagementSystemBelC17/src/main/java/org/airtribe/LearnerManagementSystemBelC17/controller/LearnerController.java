package org.airtribe.LearnerManagementSystemBelC17.controller;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.airtribe.LearnerManagementSystemBelC17.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC17.entity.LearnerDTO;
import org.airtribe.LearnerManagementSystemBelC17.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC17.exception.ValidationFailedException;
import org.airtribe.LearnerManagementSystemBelC17.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;


@RestController
public class LearnerController {

  @Autowired
  private LearnerManagementService _learnerManagementService;
  @Autowired
  private View error;

  @PostMapping("/learners")
  public Learner createLearner(@Valid @RequestBody Learner learner) {

    return _learnerManagementService.createLearner(learner);
  }

  @GetMapping("/learners")
  public List<LearnerDTO> fetchAllLearners(
      @RequestParam(value = "learnerName", required = false) String learnerName,
      @RequestParam(value = "learnerId", required = false) Long learnerId)
      throws LearnerNotFoundException, ValidationFailedException {
    List<Learner> learners = _learnerManagementService.fetchLearnerComplexParams(learnerName, learnerId);
    return _learnerManagementService.convertToLearnerDTO(learners);
  }

  @GetMapping("/learners/{learnerId}")
  public Learner fetchLearnerById(@PathVariable Long learnerId)
      throws LearnerNotFoundException, ValidationFailedException {
    return _learnerManagementService.fetchById(learnerId);
  }

  @ExceptionHandler(LearnerNotFoundException.class)
  public ResponseEntity<String> handleLearnerNotFoundException(LearnerNotFoundException exception) {
    return ResponseEntity.status(404).body(exception.getMessage());
  }

  @ExceptionHandler(ValidationFailedException.class)
  public ResponseEntity<String> handleValidationFailedException(ValidationFailedException exception) {
    return ResponseEntity.status(404).body(exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    exception.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return ResponseEntity.status(400).body(errors);
  }

//  @GetMapping("/learners/{learnerName}")
//  public List<Learner> fetchLearnerByName(@PathVariable String learnerName) {
//    return _learnerManagementService.fetchLearnerByName(learnerName);
//  }

//  @GetMapping("/learners")
//  public List<Learner>  fetchLearnerByName(@RequestParam("learnerName") String learnerName) {
//    return _learnerManagementService.fetchLearnerByName(learnerName);
//  }
}

// get all the learners
// Fetch me the learner by Id -> Single Learner
// /learners/1
// /learners?learnerId=1

// Fetch me the learner by name -> Multiple Learners
// /learners?learnerName=John
// /learners/john


// /learners/1
// /learners/john