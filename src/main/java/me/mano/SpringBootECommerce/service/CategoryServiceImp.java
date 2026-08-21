package me.mano.SpringBootECommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import me.mano.SpringBootECommerce.model.Category;

@Service
public class CategoryServiceImp implements CategoryService {
  private List<Category> categories = new ArrayList<>();
  public Long id = 1L;

  @Override
  public List<Category> getAllCategories() {
    return categories;
  }

  @Override
  public boolean createCategory(Category category) {
    category.setCategoryId(id++);
    categories.add(category);
    return true;
  }

  @Override
  public String deleteCategory(Long categoryId) {
    Category category = categories.stream() 
                                  .filter(c -> c.getCategoryId().equals(categoryId))
                                  .findFirst().orElse(null);

    if(category == null) {
      return "Category With categoryId: " + categoryId + " is Not Found";
    }

    categories.remove(category);

    return "Category With categoryId: " + categoryId + " deleted Successfully";

  }
  
}
