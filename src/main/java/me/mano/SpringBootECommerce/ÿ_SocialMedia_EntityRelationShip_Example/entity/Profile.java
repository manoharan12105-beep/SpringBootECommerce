package me.mano.SpringBootECommerce.ÿ_SocialMedia_EntityRelationShip_Example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Entity(name = "SocialProfile")
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "profile")
  // @JoinColumn(name = "user_id")
  private User user;
}
