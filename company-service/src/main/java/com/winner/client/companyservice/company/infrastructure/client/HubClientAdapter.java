package com.winner.client.companyservice.company.infrastructure.client;

import com.winner.client.companyservice.common.exception.CompanyErrorCode;
import com.winner.client.companyservice.company.application.service.port.HubPort;
import com.winner.client.companyservice.company.infrastructure.client.dto.response.HubResponse;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.global.exception.CommonErrorCode;
import com.winner.client.global.response.ApiResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HubClientAdapter implements HubPort {

  private final HubFeignClient hubFeignClient;

  @Override
  public HubResponse getHub(UUID hubId) {
      if(hubId == null){
        throw new BusinessException(CommonErrorCode.NOT_FOUND);
      }
      try {
        HubResponse response = hubFeignClient.getHub(hubId);

        if(response == null) {
          throw new BusinessException(CompanyErrorCode.HUB_NOT_FOUND);
        }
        return response;

      } catch (feign.FeignException.NotFound e) {
        log.error("허브를 찾을 수 없습니다. ID: {}", hubId);
        throw new BusinessException(CompanyErrorCode.HUB_NOT_FOUND);
      } catch (Exception e) {
        log.error("허브 서비스 통신 중 오류 발생: {}", e.getMessage());
        throw new BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR);
      }
    }
}
