package me.mano.SpringBootECommerce.service;

import java.util.List;

import me.mano.SpringBootECommerce.DTO.CategoryDTO;
import me.mano.SpringBootECommerce.DTO.CategoryResponse;
import me.mano.SpringBootECommerce.entity.Category;

public interface CategoryService {
  CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);
  
  CategoryDTO createCategory(CategoryDTO categoryDTO);

  CategoryDTO deleteCategory(Long categoryId);

  CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
