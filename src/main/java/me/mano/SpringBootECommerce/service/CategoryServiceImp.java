package me.mano.SpringBootECommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.server.ResponseStatusException;

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
                                  .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category With categoryId: " + categoryId + " is Not Found"));

    

    categories.remove(category);
    return "Category With categoryId: " + categoryId + " deleted Successfully";

  }
  
  @Override
  public Category updateCategory(Category category, Long categoryId) {
    Optional<Category> optionalCategory = categories.stream()
                                  .filter(c -> c.getCategoryId().equals(categoryId))
                                  .findFirst();
    if(optionalCategory.isPresent()) {
      Category existingCategory = optionalCategory.get();
      existingCategory.setCategoryName(category.getCategoryName());

      return existingCategory;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not found");
    }
  }

  
}
