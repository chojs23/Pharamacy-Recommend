package com.example.pharmacyrecommend.api.service

import com.example.pharmacyrecommend.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "If the address parameter is null, the requestAddressSearch method returns null."() {

        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "If the address parameter is valid, the requestAddressSearch method returns a valid document."() {
        given:
        def address = "서울 성북구 종암로 10길"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null
    }

    def "[Multiple]If the address parameter is valid, the requestAddressSearch method returns a valid document."() {
        given:
        boolean actualResult = false

        when:
        def searchResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        if (searchResult == null) actualResult = false
        else searchResult.getDocumentList().size() > 0

        where:
        inputAddress     | expectedResult
        "서울 특별시 성북구 종암동" | true
        "서울 성북구 종암동 91"  | true
        "광진구 구의동 251-45" | false


    }


}
