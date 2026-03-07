package org.airtribe.AuthenticationAuthorizationC17.service;

import org.airtribe.AuthenticationAuthorizationC17.entity.User;
import org.airtribe.AuthenticationAuthorizationC17.entity.UserDTO;
import org.airtribe.AuthenticationAuthorizationC17.entity.VerificationToken;
import org.airtribe.AuthenticationAuthorizationC17.repository.UserRepository;
import org.airtribe.AuthenticationAuthorizationC17.repository.VerificationTokenRepository;
import org.airtribe.AuthenticationAuthorizationC17.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository _userRepository;

  @Autowired
  private VerificationTokenRepository _verificationTokenRepository;

  @Autowired
  private BCryptPasswordEncoder _passwordEncoder;


  public User registerUser(UserDTO userDTO) {
    User user = new User();
    user.setUsername(userDTO.getUsername());
    user.setEnabled(false);
    user.setRole("USER");
    user.setPassword(_passwordEncoder.encode(userDTO.getPassword()));
    return _userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = _userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRole())
        .disabled(!user.getEnabled()).build();
  }

  public VerificationToken saveVerificationToken(User registeredUser, String verificationToken) {
    VerificationToken token = new VerificationToken();
    token.setToken(verificationToken);
    token.setUser(registeredUser);
    token.setExpiryDate(new java.util.Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)); // Token valid for 24 hours
    return _verificationTokenRepository.save(token);
  }

  public String verifyRegistrationTokenAndEnableUser(String token) {
    VerificationToken savedToken = _verificationTokenRepository.findByToken(token);
    if (savedToken == null) {
      return "Invalid verification token.";
    }

    if (savedToken.getExpiryDate().before(new java.util.Date())) {
      _verificationTokenRepository.delete(savedToken);
      return "Verification token has expired.";
    }

    User associatedUser = savedToken.getUser();
    associatedUser.setEnabled(true);
    _userRepository.save(associatedUser);
    _verificationTokenRepository.delete(savedToken);
    return "User verified and enabled successfully.";
  }

  // Check if the user exists
  // Check if the user is enabled
    // If the user does not exist -> Return an error message
    // If the user is not enabled -> Return an error message
  // Check if the passwords match -> You compare hashes
  // If the passwords match -> Generate the JWT TOKEN
  // If the passwords do not match -> Return an error message

  public String loginUser(String username, String password) {
    User user = _userRepository.findByUsername(username);
    if (user == null) {
      return "User not found.";
    }

    if (!user.getEnabled()) {
      return "User is not enabled. Please verify your email.";
    }

    Boolean isPasswordMatching = _passwordEncoder.matches(password, user.getPassword());
    if (!isPasswordMatching) {
      return "Invalid password.";
    }

    return JwtUtil.generateJwtToken(user);
  }
}
