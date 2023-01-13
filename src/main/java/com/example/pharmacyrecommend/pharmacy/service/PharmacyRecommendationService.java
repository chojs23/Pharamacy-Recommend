package com.example.pharmacyrecommend.pharmacy.service;

import com.example.pharmacyrecommend.api.dto.DocumentDto;
import com.example.pharmacyrecommend.api.dto.KakaoApiResponseDto;
import com.example.pharmacyrecommend.api.service.KakaoAddressSearchService;
import com.example.pharmacyrecommend.direction.entity.Direction;
import com.example.pharmacyrecommend.direction.service.DirectionService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRecommendationService {

  private final KakaoAddressSearchService kakaoAddressSearchService;
  private final DirectionService directionService;

  public void recommendPharmacyList(String address) {

    KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(
        address);

    if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(
        kakaoApiResponseDto.getDocumentList())) {
      log.error(
          "[PharmacyRecommendationService recommendPharmacyList fail] Input address: {}", address);
      return;
    }

    DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

    // 공공기관 데이터
//    List<Direction> directionList = directionService.buildDirectionList(documentDto);
    // 카카오 카테고리
    List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

    directionService.saveAll(directionList);
  }
}
