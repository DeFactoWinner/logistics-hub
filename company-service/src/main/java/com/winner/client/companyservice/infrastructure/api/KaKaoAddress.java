package com.winner.client.companyservice.infrastructure.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.winner.client.companyservice.infrastructure.service.GeocodingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class KaKaoAddress implements GeocodingService {

  @Value( "${kakao.api.key}")
  private String apiKey;

  private final RestClient restClient = RestClient.builder()
      .baseUrl( "https://dapi.kakao.com")
      .build();

  @Override
  public Double[] convert(String address){

    Double[] result = new Double[2];

    try{
     JsonNode jsonNode =  restClient.get()
          .uri(uriBuilder -> uriBuilder
          .path("/v2/local/search/address.json")
          .queryParam("query", address)
          .build())
    .header("Authorization", "KakaoAK " + apiKey)
         .retrieve()
         .body(JsonNode.class);

     if(jsonNode == null || jsonNode.get("documents").isEmpty()){
       throw new RuntimeException("주소 변환에 실패햐였습니다.");
     }
        JsonNode node = jsonNode.get("documents").get(0);
          result[0] = node.get("x").asDouble();
          result[1] = node.get("y").asDouble();

    }catch(RuntimeException e){
      log.error("카카오 요청 오류 : {}", e.getMessage());
      throw new RuntimeException("오류가 생겼습니다.");
    }
    return result;
  }

}