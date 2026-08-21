package me.mano.SpringBootECommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import me.mano.SpringBootECommerce.model.Category;
import me.mano.SpringBootECommerce.service.CategoryService;

@RestController
public class CategoryController {

  private CategoryService categoryService; // Loose coupling

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("api/public/categories")
  public List<Category> getAllCategories() {
    return categoryService.getAllCategories();
  }

  @PostMapping("api/public/categories")
  public String createCategory(@RequestBody Category category) {
    if(categoryService.createCategory(category)) {
      return "Category added Successfully";
    }
    return "Category Not Added yet";
  }


  @DeleteMapping("/api/admin/categories/{categoryId}")
  public String deleteCategory(@PathVariable Long categoryId) {
    String status = categoryService.deleteCategory(categoryId);
    return status;
  }
}
