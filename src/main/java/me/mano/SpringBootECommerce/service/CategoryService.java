package me.mano.SpringBootECommerce.service;

import java.util.List;

import me.mano.SpringBootECommerce.entity.Category;

public interface CategoryService {
  List<Category> getAllCategories();
  
  boolean createCategory(Category category);

  String deleteCategory(Long categoryId);

  Category updateCategory(Category category, Long categoryId);
}
