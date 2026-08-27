package me.mano.SpringBootECommerce.controller;

import me.mano.SpringBootECommerce.service.ProductService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import me.mano.SpringBootECommerce.DTO.ProductDto;
import me.mano.SpringBootECommerce.DTO.ProductResponse;
import me.mano.SpringBootECommerce.entity.Product;

@RestController
@RequestMapping("/api")
public class ProductController {
  
  @Autowired
  ProductService productService;

  @PostMapping("/admin/categories/{categoryId}/product")
  public ResponseEntity<ProductDto> addProduct(@RequestBody Product product, @PathVariable Long categoryId) {
    ProductDto productDto = productService.addProduct(categoryId, product);
    return new ResponseEntity<>(productDto, HttpStatus.CREATED);
  }

  @GetMapping("/public/produts")
  public ResponseEntity<ProductResponse> getProduct() {
    ProductResponse productResponse = productService.getProduct();
    return new ResponseEntity<>(productResponse, HttpStatus.OK);
  }

  @GetMapping("/public/categories/{categoryId}/products")
  public ResponseEntity<ProductResponse> getAllProdByCategoryId(@PathVariable Long categoryId) {
    ProductResponse productResponse = productService.getAllProdByCategoryId(categoryId);
    return new ResponseEntity<>(productResponse, HttpStatus.OK);
  }

  @GetMapping("/public/products/keyword/{keyword}")
  public ResponseEntity<ProductResponse> getProdByKeyword(@PathVariable String keyword) {
    ProductResponse productResponse = productService.getProdByKeyword(keyword);
    return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
  }

  @PutMapping("/admin/products/update/{productId}")
  public ResponseEntity<ProductDto> updateProduct(@RequestBody Product product, @PathVariable Long productId) {
    ProductDto UpdatedProductDto = productService.updateProduct(product, productId);
    return new ResponseEntity<>(UpdatedProductDto, HttpStatus.ACCEPTED);
  }

  @DeleteMapping("/admin/product/delete/{productId}")
  public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long productId) {
    ProductDto deletedProductDto = productService.deleteProduct(productId);
    return new ResponseEntity<>(deletedProductDto, HttpStatus.ACCEPTED);
  }




  @PutMapping("/admin/products/{productId}/image")
  public ResponseEntity<ProductDto> updateProductImage(@PathVariable Long productId, 
                                                       @RequestParam("image") MultipartFile image) throws IOException {
    ProductDto updatedProduct = productService.updateProductImage(productId, image);
    return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
  }
}
