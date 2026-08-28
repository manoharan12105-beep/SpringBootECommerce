package me.mano.SpringBootECommerce.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity(name = "Product")
@Data
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @NotBlank(message = "Product name is required")
  @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
  private String productName;

  @NotBlank(message = "Image filename cannot be empty")
  private String image;

  @Size(max = 500, message = "Description cannot exceed 500 characters")
  private String description;

  @PositiveOrZero(message = "Quantity cannot be negative")
  private Integer quantity;

  @NotNull(message = "Price is required")
  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
  private double price;

  @DecimalMin(value = "0.0", message = "Discount cannot be negative")
  @DecimalMax(value = "100.0", message = "Discount cannot exceed 100%")
  private double discount;

  @DecimalMin(value = "0.0", message = "Special price must be non-negative")
  private double specialPrice;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
