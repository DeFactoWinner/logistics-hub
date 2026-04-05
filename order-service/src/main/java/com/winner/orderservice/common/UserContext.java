package com.winner.orderservice.common;

import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
public class UserContext {
  private final UUID userId;
  private final UserRole role;
  private final UUID relationId; // companyId or hubId depending on role

  public UserContext(UUID userId, UserRole role, UUID relationId) {
    this.userId = userId;
    this.role = role;
    this.relationId = relationId;
  }

  @JsonIgnore
  public UUID getCompanyId() {
    if (isCompany()) {
      return relationId;
    }
    return null; // Or throw an exception
  }

  @JsonIgnore
  public UUID getHubId() {
    if (isHub() || isDelivery()) {
      return relationId;
    }
    return null; // Or throw an exception
  }

  @JsonIgnore
  public boolean isMaster() { return role == UserRole.MASTER; }

  @JsonIgnore
  public boolean isHub() { return role == UserRole.HUB_MANAGER; }

  @JsonIgnore
  public boolean isCompany() { return role == UserRole.COMPANY_MANAGER; }

  @JsonIgnore
  public boolean isDelivery() { return role == UserRole.DELIVERY_MANAGER; }

  public boolean hasRole(UserRole... roles) {
    for (UserRole r : roles) {
      if (Objects.equals(this.role, r)) return true;
    }
    return false;
  }
}
