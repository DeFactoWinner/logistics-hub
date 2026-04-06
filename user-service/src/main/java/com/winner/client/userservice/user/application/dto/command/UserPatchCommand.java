package com.winner.client.userservice.user.application.dto.command;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UserPatchCommand(
    UUID userId,
    String name,
    String phoneNumber,
    String slackId
) {

}
