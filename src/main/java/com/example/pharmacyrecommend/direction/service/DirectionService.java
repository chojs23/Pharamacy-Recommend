package com.example.pharmacyrecommend.direction.service;

import com.example.pharmacyrecommend.api.dto.DocumentDto;
import com.example.pharmacyrecommend.api.service.KakaoCategorySearchService;
import com.example.pharmacyrecommend.direction.entity.Direction;
import com.example.pharmacyrecommend.direction.repository.DirectionRepository;
import com.example.pharmacyrecommend.pharmacy.service.PharmacySearchService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

  // Maximum number of pharmacies to be recommended
  private static final int MAX_SEARCH_COUNT = 3;
  // Maximum distance to be recommended
  private static final double RADIUS_KM = 10.0;

  private final PharmacySearchService pharmacySearchService;
  private final DirectionRepository directionRepository;
  private final KakaoCategorySearchService kakaoCategorySearchService;

  @Transactional
  public List<Direction> saveAll(List<Direction> directions) {
    if (CollectionUtils.isEmpty(directions)) {
      return Collections.emptyList();
    }
    return directionRepository.saveAll(directions);
  }

  public List<Direction> buildDirectionList(DocumentDto documentDto) {

    if (Objects.isNull(documentDto)) {
      return Collections.emptyList();
    }

    // Search Pharmacy Data
    // Calculate Distance then sort
    return pharmacySearchService.searchPharmacyDtoList()
        .stream().map(pharmacyDto ->
            Direction.builder()
                .inputAddress(documentDto.getAddressName())
                .inputLatitude(documentDto.getLatitude())
                .inputLongitude(documentDto.getLongitude())
                .targetPharmacyName(pharmacyDto.getName())
                .targetAddress(pharmacyDto.getAddress())
                .targetLatitude(pharmacyDto.getLatitude())
                .targetLongitude(pharmacyDto.getLongitude())
                .distance(calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                    pharmacyDto.getLatitude(), pharmacyDto.getLongitude())
                )
                .build())
        .filter(direction -> direction.getDistance() <= RADIUS_KM)
        .sorted((Comparator.comparingDouble(Direction::getDistance)))
        .limit(MAX_SEARCH_COUNT)
        .collect(Collectors.toList());
  }

  // pharmacy search by category kakao api
  public List<Direction> buildDirectionListByCategoryApi(DocumentDto inputDocumentDto) {
    if (Objects.isNull(inputDocumentDto)) {
      return Collections.emptyList();
    }

    return kakaoCategorySearchService.requestCategorySearch(inputDocumentDto.getLatitude(),
            inputDocumentDto.getLongitude(), RADIUS_KM).getDocumentList()
        .stream().map(resultDocumentDto ->
            Direction.builder()
                .inputAddress(inputDocumentDto.getAddressName())
                .inputLatitude(inputDocumentDto.getLatitude())
                .inputLongitude(inputDocumentDto.getLongitude())
                .targetPharmacyName(resultDocumentDto.getPlaceName())
                .targetAddress(resultDocumentDto.getAddressName())
                .targetLatitude(resultDocumentDto.getLatitude())
                .targetLongitude(resultDocumentDto.getLongitude())
                .distance(resultDocumentDto.getDistance() * 0.001) // KM
                .build())
        .limit(MAX_SEARCH_COUNT)
        .collect(Collectors.toList());

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
