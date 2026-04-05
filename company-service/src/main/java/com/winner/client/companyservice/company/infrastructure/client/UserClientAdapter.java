package com.winner.client.companyservice.company.infrastructure.client;

import com.winner.client.companyservice.company.application.service.port.UserPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserClientAdapter implements UserPort {

  private final UserFeignClient feignClient;

  @Override
  public void unassignUsersByCompany(UUID companyId) {
    feignClient.unassignUser(companyId);
  }
}
