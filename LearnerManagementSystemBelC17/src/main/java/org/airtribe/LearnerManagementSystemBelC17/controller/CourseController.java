package org.airtribe.LearnerManagementSystemBelC17.controller;

import org.airtribe.LearnerManagementSystemBelC17.entity.Course;
import org.airtribe.LearnerManagementSystemBelC17.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CourseController {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @PostMapping("/courses")
  public Course createCourse(@RequestBody Course course) {
    return _learnerManagementService.createCourse(course);


  }
}
