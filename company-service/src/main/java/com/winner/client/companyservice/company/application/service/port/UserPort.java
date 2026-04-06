package com.winner.client.companyservice.company.application.service.port;

import java.util.UUID;

public interface UserPort {
  void unassignUsersByCompany(UUID companyId);
}
