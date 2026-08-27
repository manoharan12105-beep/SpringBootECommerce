package me.mano.SpringBootECommerce.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import me.mano.SpringBootECommerce.DTO.ProductDto;
import me.mano.SpringBootECommerce.DTO.ProductResponse;
import me.mano.SpringBootECommerce.entity.Product;

@Service
public interface ProductService {

  ProductDto addProduct(Long categoryId, Product product);

  ProductResponse getProduct();

  ProductResponse getAllProdByCategoryId(Long categoryId);

  ProductResponse getProdByKeyword(String keyword);

  ProductDto updateProduct(Product product, Long categoryId);

  ProductDto deleteProduct(Long productId);

  ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException;
  
}
