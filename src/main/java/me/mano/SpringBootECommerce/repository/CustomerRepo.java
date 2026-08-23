package me.mano.SpringBootECommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.mano.SpringBootECommerce.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
  
}
