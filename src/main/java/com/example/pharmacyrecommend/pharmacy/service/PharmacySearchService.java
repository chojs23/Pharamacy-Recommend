package com.example.pharmacyrecommend.pharmacy.service;

import com.example.pharmacyrecommend.pharmacy.dto.PharmacyDto;
import com.example.pharmacyrecommend.pharmacy.entity.Pharmacy;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {

  private final PharmacyRepositoryService pharmacyRepositoryService;

  public List<PharmacyDto> searchPharmacyDtoList() {

    // redis

    // db
    return pharmacyRepositoryService.findAll().stream().map(entity -> convertToPharmacyDto(entity))
        .collect(Collectors.toList());
  }

  private PharmacyDto convertToPharmacyDto(Pharmacy pharmacy) {
    // convert
    return PharmacyDto.builder()
        .id(pharmacy.getId())
        .name(pharmacy.getPharmacyName())
        .address(pharmacy.getPharmacyAddress())
        .latitude(pharmacy.getLatitude())
        .longitude(pharmacy.getLongitude())
        .build();
  }

}