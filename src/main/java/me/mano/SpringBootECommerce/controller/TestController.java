package me.mano.SpringBootECommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import me.mano.SpringBootECommerce.DTO.LoginRequest;
import me.mano.SpringBootECommerce.DTO.LoginResponse;
import me.mano.SpringBootECommerce.security.JwtUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;

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

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    Authentication authentication;
    try {
      authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          loginRequest.getUsername(), 
          loginRequest.getPassword()
        )
      );
    } catch (AuthenticationException e) {
      Map<String, Object> map = new HashMap<>();
      map.put("message","Bad Credentials");
      map.put("status", false);

      return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).toList();
    LoginResponse loginResponse = new LoginResponse(userDetails.getUsername(), jwtToken, roles);
    
    return new ResponseEntity<>(loginResponse, HttpStatus.CONTINUE);
  }
}
