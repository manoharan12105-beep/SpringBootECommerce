package me.mano.SpringBootECommerce.service;

import java.io.IOException;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import me.mano.SpringBootECommerce.DTO.ProductDto;
import me.mano.SpringBootECommerce.DTO.ProductResponse;
import me.mano.SpringBootECommerce.entity.Category;
import me.mano.SpringBootECommerce.entity.Product;
import me.mano.SpringBootECommerce.exception.APIException;
import me.mano.SpringBootECommerce.exception.ResourceNotFoundException;
import me.mano.SpringBootECommerce.repository.CategoryRepo;
import me.mano.SpringBootECommerce.repository.ProductRepo;

@Service
public class ProductServiceImp implements ProductService {

  @Autowired
  private ProductRepo productRepo;

  @Autowired
  private CategoryRepo categoryRepo;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private FileService fileService;

  @Value("${project.image}")
  private String path;

  @Override
  public ProductDto addProduct(Long categoryId, Product product) {
    Category category = categoryRepo.findById(categoryId)
      .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));


    boolean ifProductPresent = true;
    List<Product> products = category.getProducts();
    for(Product prod : products) {
      if(prod.getProductName().equals(product.getProductName())) {
        ifProductPresent = false;
        break;
      }
    }

    if(ifProductPresent) {
      product.setImage("default.png");
      product.setCategory(category);
  
      double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
      product.setSpecialPrice(specialPrice);
  
      Product savedProduct = productRepo.save(product);
      return modelMapper.map(savedProduct, ProductDto.class);
    } else {
      throw new APIException("Product already exist!!");
    }

  }

  @Override
  public ProductResponse getProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
    Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    Page<Product> productPage = productRepo.findAll(pageDetails);
    
    List<Product> products = productPage.getContent();
    List<ProductDto> productDto = products.stream()
      .map(product -> modelMapper.map(product, ProductDto.class))
      .toList();
    
    if(products.isEmpty()) {
      throw new APIException("No product present");
    }

    return new ProductResponse(productDto);
  }

  @Override
  public ProductResponse getAllProdByCategoryId(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, Long categoryId) {
    
    Category category = categoryRepo.findById(categoryId)
    .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

    Sort sortByAndOrder = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    Page<Product> productPage = productRepo.findByCategoryOrderByPriceAsc(category, pageDetails);

    List<Product> products = productPage.getContent();
    List<ProductDto> productDto = products.stream()
      .map(product -> modelMapper.map(product, ProductDto.class))
      .toList();

    return new ProductResponse(productDto);
  }

  @Override
  public ProductResponse getProdByKeyword(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword) {
    Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") 
          ? Sort.by(sortBy).ascending()
          : Sort.by(sortBy).descending();

    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    String parameter = '%' + keyword + '%';
    Page<Product> productPage = productRepo.findByProductNameLikeIgnoreCase(parameter, pageDetails);

    List<Product> products = productPage.getContent();
    List<ProductDto> productDto = products.stream()
      .map(product -> modelMapper.map(product, ProductDto.class))
      .toList();

    if(products.isEmpty()) {
      throw new APIException("No product present with the keyword : " + keyword);
    }

    return new ProductResponse(productDto);
  }

  @Override
  public ProductDto updateProduct(Product product, Long productId) {
    Product productFromDb = productRepo.findById(productId)
      .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

    productFromDb.setProductName(product.getProductName());
    productFromDb.setDescription(product.getDescription());
    productFromDb.setQuantity(product.getQuantity());
    productFromDb.setDiscount(product.getDiscount());
    productFromDb.setPrice(product.getPrice());

    double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
    productFromDb.setSpecialPrice(specialPrice);

    Product savedProduct = productRepo.save(productFromDb);
    return modelMapper.map(savedProduct, ProductDto.class);
  }

  @Override
  public ProductDto deleteProduct(Long productId) {
    Product product = productRepo.findById(productId)
      .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

    productRepo.delete(product);
    return modelMapper.map(product, ProductDto.class);
  }

  @Override
  public ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException {
    Product productFromDb = productRepo.findById(productId)
      .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

    // String path = "images";
    String fileName = fileService.uploadImage(path, image);

    productFromDb.setImage(fileName);
    Product updatedProduct = productRepo.save(productFromDb);

    return modelMapper.map(updatedProduct, ProductDto.class);
  }

  
}
