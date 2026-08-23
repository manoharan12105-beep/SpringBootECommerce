package me.mano.SpringBootECommerce.service;

import java.util.List;

import me.mano.SpringBootECommerce.entity.Customer;

public interface CustomerService {
  Customer getCustomerById(long customerId);
  List<Customer> getAllCustomers();

  Customer addCustomer(Customer customer);

  Customer updateCustomer(Customer customer, long customerId);

  boolean deleteCustomerById(long customerId);
}
