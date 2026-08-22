package me.mano.SpringBootECommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;
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
    List<Category> categories = categoryRepo.findAll();

    Category category = categories.stream() 
                                  .filter(c -> c.getCategoryId().equals(categoryId))
                                  .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category With categoryId: " + categoryId + " is Not Found"));

    

    categoryRepo.delete(category);
    return "Category With categoryId: " + categoryId + " deleted Successfully";

  }
  
  @Override
  public Category updateCategory(Category category, Long categoryId) {
    List<Category> categories = categoryRepo.findAll();

    Optional<Category> optionalCategory = categories.stream()
                                  .filter(c -> c.getCategoryId().equals(categoryId))
                                  .findFirst();
    if(optionalCategory.isPresent()) {
      Category existingCategory = optionalCategory.get();
      existingCategory.setCategoryName(category.getCategoryName());

      Category savedCategory = categoryRepo.save(existingCategory);

      return savedCategory;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not found");
    }
  }

  
}
