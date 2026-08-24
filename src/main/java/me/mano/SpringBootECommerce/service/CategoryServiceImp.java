package me.mano.SpringBootECommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import me.mano.SpringBootECommerce.DTO.CategoryDTO;
import me.mano.SpringBootECommerce.DTO.CategoryResponse;
import me.mano.SpringBootECommerce.entity.Category;
import me.mano.SpringBootECommerce.exception.APIException;
import me.mano.SpringBootECommerce.exception.ResourceNotFoundException;
import me.mano.SpringBootECommerce.repository.CategoryRepo;

@Service
public class CategoryServiceImp implements CategoryService {
  @Autowired
  private CategoryRepo categoryRepo;


  @Autowired
  private ModelMapper modelMapper;

  @Override
  public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {
    Pageable pageDetails = PageRequest.of(pageNumber, pageSize);
    Page<Category> categoryPage = categoryRepo.findAll(pageDetails);

    List<Category> categories = categoryPage.getContent();
    if(categories.isEmpty())
      throw new APIException("No category create till now.");

    List<CategoryDTO> categoryDTOs = categories.stream()
                                                .map(category -> modelMapper.map(category, CategoryDTO.class))
                                                .collect(Collectors.toList());

    CategoryResponse categoryResponse = new CategoryResponse();
    categoryResponse.setContent(categoryDTOs);
    return categoryResponse;
  }

  @Override
  public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    Category category = modelMapper.map(categoryDTO, Category.class);
    Category savedCategory = categoryRepo.findByCategoryName(category.getCategoryName());
    if(savedCategory != null) {
      throw new APIException("Category with the name \"" + category.getCategoryName() + "\" already exists !!!");
    }
    Category savedCategoryFromDB = categoryRepo.save(category);

    return modelMapper.map(savedCategoryFromDB, CategoryDTO.class);
  }

  @Override
  public CategoryDTO deleteCategory(Long categoryId) {
    Category savedCategory = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    categoryRepo.delete(savedCategory);

    return modelMapper.map(savedCategory, CategoryDTO.class);
  }
  
  @Override
  public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
    Category savedCategory = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

    savedCategory.setCategoryName(categoryDTO.getCategoryName());
    categoryRepo.save(savedCategory);

    return modelMapper.map(savedCategory, CategoryDTO.class);
  }
}
