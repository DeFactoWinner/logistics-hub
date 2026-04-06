package com.winner.client.userservice.user.application.dto.command;

import lombok.Builder;

@Builder
public record UserPatchCommand(
    String name,
    String phoneNumber,
    String slackId
) {

}
