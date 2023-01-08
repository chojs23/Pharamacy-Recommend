package com.example.pharmacyrecommend.api.service;


import com.example.pharmacyrecommend.api.dto.KakaoApiResponseDto;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

  private final RestTemplate restTemplate;
  private final KakaoUriBuilderService kakaoUriBuilderService;

  @Value("${kakao.rest.api.key}")
  private String kakaoRestApiKey;

  @Retryable(
      value = {RuntimeException.class},
      maxAttempts = 2,
      backoff = @org.springframework.retry.annotation.Backoff(delay = 2000)
  )
  public KakaoApiResponseDto requestAddressSearch(String address) {
    if (ObjectUtils.isEmpty(address)) {
      return null;
    }
    URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);

    HttpHeaders headers = new HttpHeaders();

    headers.add(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
    HttpEntity httpEntity = new HttpEntity<>(headers);

    // kakao api 호출

    return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class)
        .getBody();
  }

  @Recover
  public KakaoApiResponseDto recover(RuntimeException e, String address) {
    log.error("All the retries failed, address: {}, error: {}", address, e.getMessage());
    return null;
  }


}
