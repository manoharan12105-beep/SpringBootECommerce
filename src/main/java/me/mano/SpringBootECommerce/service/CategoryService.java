package me.mano.SpringBootECommerce.service;

import java.util.List;

import me.mano.SpringBootECommerce.model.Category;

public interface CategoryService {
  List<Category> getAllCategories();
  
  boolean createCategory(Category category);

  String deleteCategory(Long categoryId);
}
