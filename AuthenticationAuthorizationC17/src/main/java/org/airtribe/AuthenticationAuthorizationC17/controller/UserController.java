package org.airtribe.AuthenticationAuthorizationC17.controller;

import java.util.UUID;
import org.airtribe.AuthenticationAuthorizationC17.entity.User;
import org.airtribe.AuthenticationAuthorizationC17.entity.UserDTO;
import org.airtribe.AuthenticationAuthorizationC17.entity.VerificationToken;
import org.airtribe.AuthenticationAuthorizationC17.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  @Autowired
  private UserService _userService;

  @PostMapping("/register")
  public User registerUser(@RequestBody UserDTO userDTO) {
    User registeredUser =  _userService.registerUser(userDTO);
    String verificationToken = UUID.randomUUID().toString();
    String verificationUrl = "http://localhost:3001/verifyRegistrationToken?token=" + verificationToken;
    System.out.println("Please verify your registration by clicking on the following link: " + verificationUrl);
    VerificationToken token = _userService.saveVerificationToken(registeredUser, verificationToken);
    System.out.println("Verification token saved: " + token.getToken());
    return registeredUser;
  }

  @PostMapping("/verifyRegistrationToken")
  public String verifyRegistrationToken(@RequestParam String token) {
    return _userService.verifyRegistrationTokenAndEnableUser(token);
  }

  @GetMapping("/")
  public String home() {
    return "Welcome to the home page!";
  }

  @GetMapping("/hello")
  @PreAuthorize("hasAnyRole('USER')")
  public String hello() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    System.out.println("Authenticated user: " + authentication.getName());
//    authentication.getAuthorities().forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));
//
//    if (authentication.getAuthorities().toString().equals("[ROLE_USER]")) {
//      return "User does not have privilege to access this endpoint";
//    }
    return "Hello, World!";
  }

  @PostMapping("/signin")
  public String signin(@RequestParam("username") String username, @RequestParam("password") String password) {
    return _userService.loginUser(username, password);
  }

}
