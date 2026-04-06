package com.winner.client.companyservice.company.application.service.port;

import com.winner.client.companyservice.company.infrastructure.client.dto.response.HubResponse;
import java.util.UUID;

public interface HubPort {

  HubResponse getHub(UUID hubId);

}
