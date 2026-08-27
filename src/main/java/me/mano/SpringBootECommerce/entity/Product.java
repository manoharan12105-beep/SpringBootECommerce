package me.mano.SpringBootECommerce.entity;

import org.hibernate.tool.schema.spi.GenerationTarget;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "Product")
@Data
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long produuctId;
  private String productName;
  private String image;
  private String description;
  private Integer quantity;
  private double price;
  private double discount;
  private double specialPrice;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
