package com.winner.client.userservice.user.application.dto.command;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UnAssignManagersCommand(
    UUID referenceId
) {

  public static UnAssignManagersCommand from(UUID referenceId) {
    return UnAssignManagersCommand.builder()
        .referenceId(referenceId)
        .build();
  }
}
