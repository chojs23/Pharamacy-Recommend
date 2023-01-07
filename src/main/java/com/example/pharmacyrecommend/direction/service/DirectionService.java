package com.example.pharmacyrecommend.direction.service;

import com.example.pharmacyrecommend.api.dto.DocumentDto;
import com.example.pharmacyrecommend.direction.entity.Direction;
import com.example.pharmacyrecommend.pharmacy.dto.PharmacyDto;
import com.example.pharmacyrecommend.pharmacy.service.PharmacySearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

  private final PharmacySearchService pharmacySearchService;

  public List<Direction> buildDirectionList(DocumentDto documentDto) {

    // Search Pharmacy Data
    List<PharmacyDto> pharmacyDtoList = pharmacySearchService.searchPharmacyDtoList();
    // Calculate Distance then sort

    // Return List
    return null;
  }

  // Haversine formula
  private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    double R = 6372.8; // In kilometers
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);

    double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1)
        * Math.cos(lat2);
    double c = 2 * Math.asin(Math.sqrt(a));
    return R * c;
  }
}
