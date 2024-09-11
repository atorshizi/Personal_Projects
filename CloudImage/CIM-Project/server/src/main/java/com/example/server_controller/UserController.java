package com.example.server_controller; 

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UserController {
  // private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserRepository userRepository;
  
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository; 
  } 
  @GetMapping("/user")
  public ResponseEntity<Iterable<User>> findAllUsers() { 
    return ResponseEntity.ok(this.userRepository.findAll()); 
  } 
  @PostMapping("/auth/login") 
  public ResponseEntity<Response> loginWithCreds(@RequestBody Creds inputedCreds) { 
    User foundUser = this.userRepository.login(inputedCreds.getusername(),inputedCreds.getpassword()); 
    if (foundUser!=null){ 
      final String jwt = jwtUtils.genToken(foundUser.getusername()); 
      return ResponseEntity.status(HttpStatus.OK).body(new Response("Login Success", jwt)); 
    } else { 
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("Login Failed", null)); 
    }
  }
  @PostMapping("/user")
  public ResponseEntity<Response> addUser(@RequestBody Creds newUser) {
    User userInDb = this.userRepository.checkUsername(newUser.getusername()); 
    if (userInDb == null){ 
      this.userRepository.save(new User(newUser.getusername(), newUser.getpassword())); 
      return ResponseEntity.status(HttpStatus.OK).body(new Response("Account Creation Success", null)); 
    } else { 
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Account Creation Failed", null)); 
    }
  } 
}