package me.mano.SpringBootECommerce.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
  private String username;
  private String jwtToken;
  private List<String> roles;
}
