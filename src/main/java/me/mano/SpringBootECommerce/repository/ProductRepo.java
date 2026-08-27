package me.mano.SpringBootECommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.mano.SpringBootECommerce.entity.Category;
import me.mano.SpringBootECommerce.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{
  List<Product> findByCategoryOrderByPriceAsc(Category category);

  List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
