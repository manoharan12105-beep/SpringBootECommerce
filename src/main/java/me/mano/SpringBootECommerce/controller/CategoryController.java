package me.mano.SpringBootECommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import me.mano.SpringBootECommerce.model.Category;

@RestController
public class CategoryController {
  
  private List<Category> categories = new ArrayList<>();

  @GetMapping("api/public/categories")
  public List<Category> getAllCategories() {
    return categories;
  }
}
