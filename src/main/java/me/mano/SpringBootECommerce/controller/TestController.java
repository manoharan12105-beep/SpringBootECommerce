package me.mano.SpringBootECommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping("/hello")
  public ResponseEntity<String> sayHello() {
    return new ResponseEntity<>("Hello From Test Endpoint.", HttpStatus.OK);
  }
  
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/user")
  public ResponseEntity<String> greetUser() {
    return new ResponseEntity<>("Hello User.", HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/admin")
  public ResponseEntity<String> greetAdmin() {
    return new ResponseEntity<>("Hello Admin.", HttpStatus.OK);
  }

  @GetMapping("/noAuth")
  public ResponseEntity<String> urlPermitExample() {
    return new ResponseEntity<>("You are accessing this URL without authentication.", HttpStatus.OK);
  }
}
