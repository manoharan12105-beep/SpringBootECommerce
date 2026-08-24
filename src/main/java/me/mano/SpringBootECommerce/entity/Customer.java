package me.mano.SpringBootECommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity(name = "Customers")
@Data
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long customerId;
  private String firstName;
  private String lastName;

  @NotBlank(message = "Email cannot be Blank")
  @Email(message = "Enter in email Format")
  private String email;
  private String phoneNumber;
}
