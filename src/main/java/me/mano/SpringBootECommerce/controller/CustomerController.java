package me.mano.SpringBootECommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import me.mano.SpringBootECommerce.entity.Customer;
import me.mano.SpringBootECommerce.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {

  @Autowired
  private CustomerService customerService;
  
  @GetMapping("/public/customer")
  public ResponseEntity<List<Customer>> getAllCustomer() {
    List<Customer> customers = customerService.getAllCustomers();
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @GetMapping("/public/customer/{customerId}")
  public ResponseEntity<?> getCustomer(@PathVariable Long customerId) {
    try {
      Customer customer = customerService.getCustomerById(customerId);
      return new ResponseEntity<>(customer, HttpStatus.FOUND);
    } catch(ResponseStatusException e) {
      return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }
  }

  @PostMapping("/admin/addCustomer")
  public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
    Customer addedCustomer = customerService.addCustomer(customer);
    return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
  }

  @PutMapping("/admin/updateCustomer/{customerId}")
  public ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer customer, @PathVariable long customerId) {
    try {
      Customer updatedCustomer = customerService.updateCustomer(customer, customerId);
      return new ResponseEntity<>(updatedCustomer, HttpStatus.ACCEPTED);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }
  }

  @DeleteMapping("/admin/deleteCustomer/{customerId}")
  public ResponseEntity<String> deleteCustomer(@PathVariable long customerId) {
    try {
      boolean deleted = customerService.deleteCustomerById(customerId);
      if(deleted)
        return new ResponseEntity<>("Customer with the ID : " + customerId + " Has been deleted Successfully", HttpStatus.ACCEPTED);

      return new ResponseEntity<>("Something went Wrong, Customer with the ID : " + customerId + " hasn't  deleted yet", HttpStatus.CONFLICT);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }
  }
}
