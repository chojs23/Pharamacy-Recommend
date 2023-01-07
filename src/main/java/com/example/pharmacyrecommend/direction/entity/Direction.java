package com.example.pharmacyrecommend.direction.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "direction")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Direction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // User
  private String inputAddress;
  private String inputLatitude;
  private double inputLongitude;

  // Pharmacy's address
  private String targetPharmacyName;
  private String targetAddress;
  private double targetLatitude;
  private double targetLongitude;

  // Distance between user and pharmacy
  private double distance;
}
