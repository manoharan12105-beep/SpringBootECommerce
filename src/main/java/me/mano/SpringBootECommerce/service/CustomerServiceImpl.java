package me.mano.SpringBootECommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import me.mano.SpringBootECommerce.entity.Customer;
import me.mano.SpringBootECommerce.repository.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {
  @Autowired
  private CustomerRepo customerRepo;
  
  @Override
  public Customer getCustomerById(long customerId) {
    Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer With id " + customerId + " not found."));

    return customer;
  }

  @Override
  public List<Customer> getAllCustomers() {
    List<Customer> customers = customerRepo.findAll();
    return customers;
  }

  @Override
  public Customer addCustomer(Customer customer) {
    return customerRepo.save(customer);
  }

  @Override
  public Customer updateCustomer(Customer customer, long customerId) {
    Customer savedCustomer = customerRepo.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer With id " + customerId + " not found."));

    if(customer.getFirstName() != null)
      savedCustomer.setFirstName(customer.getFirstName());
    
    if(customer.getLastName() != null)
      savedCustomer.setLastName(customer.getLastName());
 
    if(customer.getEmail() != null) 
      savedCustomer.setEmail(customer.getEmail());

    if(customer.getPhoneNumber() != null)
      savedCustomer.setPhoneNumber(customer.getPhoneNumber());


    return customerRepo.save(savedCustomer); 
  }

  @Override
  public boolean deleteCustomerById(long customerId) {
    Customer savedCustomer = customerRepo.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer With id " + customerId + " not found."));

    customerRepo.delete(savedCustomer);
    return true;
  }
  
}
