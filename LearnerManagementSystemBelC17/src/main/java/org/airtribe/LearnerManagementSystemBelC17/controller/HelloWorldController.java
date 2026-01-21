package org.airtribe.LearnerManagementSystemBelC17.controller;

import jakarta.annotation.Resource;
import org.airtribe.LearnerManagementSystemBelC17.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {

  @Autowired
  private HelloWorldService helloWorldService;

  @GetMapping("/")
  public String entry() {
    return "Welcome to learner management system";
  }

  @GetMapping("/hello")
  public String hello() {
    return helloWorldService.handleHelloWorld();
  }


  @GetMapping("/hello2")
  public String hello2() {
    return helloWorldService.handleHelloWorld();
  }

}
