package me.mano.SpringBootECommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import me.mano.SpringBootECommerce.DTO.CategoryDTO;
import me.mano.SpringBootECommerce.DTO.CategoryResponse;
import me.mano.SpringBootECommerce.entity.Category;
import me.mano.SpringBootECommerce.service.CategoryService;

@RestController
public class CategoryController {

  private CategoryService categoryService; // Loose coupling
 
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("api/public/categories")
  public ResponseEntity<CategoryResponse> getAllCategories() {
    CategoryResponse categories = categoryService.getAllCategories();
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @PostMapping("api/public/categories")
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
    CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);

    return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
  }


  @DeleteMapping("/api/admin/categories/{categoryId}")
  public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
    CategoryDTO categoryDTO = categoryService.deleteCategory(categoryId);

    return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
  }

  @PutMapping("/api/admin/categories/update/{categoryId}")
  public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {
    CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);

    return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.ACCEPTED);
  }
}
