package com.example.pharmacyrecommend.api.service;


import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class KakaoUriBuilderService {

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    public URI buildUriByAddressSearch(String address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(
                KAKAO_LOCAL_SEARCH_ADDRESS_URL)
            .queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();
        log.info("[KakaoUriBuilderService buildUriByAddressSearch] address: {},uri : {}", address,
            uri);
        return uri;
    }

    public URI buildUriByCategorySearch(double latitude, double longitude, double radius,
        String category) {

        double meterRadius = radius * 1000;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(
                KAKAO_LOCAL_CATEGORY_SEARCH_URL)
            .queryParam("category_group_code", category)
            .queryParam("x", longitude)
            .queryParam("y", latitude)
            .queryParam("radius", meterRadius)
            .queryParam("sort", "distance");

        URI uri = uriBuilder.build().encode().toUri();
        log.info(
            "[KakaoUriBuilderService buildUriByCategorySearch] latitude: {}, longitude: {}, radius: {}, category: {}, uri: {}",
            latitude, longitude, radius, category, uri);
        return uri;
    }
}
