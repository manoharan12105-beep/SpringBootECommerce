package me.mano.SpringBootECommerce.controller;

import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.CardException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import me.mano.SpringBootECommerce.model.Category;
import me.mano.SpringBootECommerce.service.CategoryService;

@RestController
public class CategoryController {

  private CategoryService categoryService; // Loose coupling

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("api/public/categories")
  public ResponseEntity<List<Category>> getAllCategories() {
    List<Category> categories = categoryService.getAllCategories();
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @PostMapping("api/public/categories")
  public ResponseEntity createCategory(@RequestBody Category category) {
    categoryService.createCategory(category);
    return new ResponseEntity<>("Category added Successfully", HttpStatus.CREATED);
  }


  @DeleteMapping("/api/admin/categories/{categoryId}")
  public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
    try {
      String status = categoryService.deleteCategory(categoryId);
      // return new ResponseEntity<>(status, HttpStatus.OK);
      return ResponseEntity.ok("Category with categoryId " + categoryId + " Has been removed Successfully");
      } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }
  }

  @PutMapping("/api/admin/categories/update/{categoryId}")
  public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {
    try {
      Category updatedCategory = categoryService.updateCategory(category, categoryId);
      return new ResponseEntity<>("Category with category id: " + categoryId + " Has been Updated Successfully.\n\n" + updatedCategory, HttpStatus.ACCEPTED);
    } catch(ResponseStatusException e) {
      return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }
  }
}
