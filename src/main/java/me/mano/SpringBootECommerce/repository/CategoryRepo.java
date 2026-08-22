package me.mano.SpringBootECommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.mano.SpringBootECommerce.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
  
}
