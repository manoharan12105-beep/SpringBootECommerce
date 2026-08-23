package me.mano.SpringBootECommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import me.mano.SpringBootECommerce.entity.Category;
import me.mano.SpringBootECommerce.repository.CategoryRepo;

@Service
public class CategoryServiceImp implements CategoryService {
  // private List<Category> categories = new ArrayList<>();
  // public Long id = 1L;

  @Autowired
  private CategoryRepo categoryRepo;

  @Override
  public List<Category> getAllCategories() {
    return categoryRepo.findAll();
  }

  @Override
  public boolean createCategory(Category category) {
    categoryRepo.save(category);
    return true;
  }

  @Override
  public String deleteCategory(Long categoryId) {
    Category savedCategory = categoryRepo.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found.."));
    categoryRepo.delete(savedCategory);

    return "Category With categoryId: " + categoryId + " deleted Successfully";
  }
  
  @Override
  public Category updateCategory(Category category, Long categoryId) {
    Category savedCategory = categoryRepo.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not found"));

    savedCategory.setCategoryName(category.getCategoryName());
    return categoryRepo.save(category);
  }
}
