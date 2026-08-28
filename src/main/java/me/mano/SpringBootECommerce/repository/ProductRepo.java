package me.mano.SpringBootECommerce.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.mano.SpringBootECommerce.entity.Category;
import me.mano.SpringBootECommerce.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{
  Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

  Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);
}
