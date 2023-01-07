package com.example.pharmacyrecommend.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyDto {

  private Long id;
  private String name;
  private String address;
  private double latitude;
  private double longitude;
}
