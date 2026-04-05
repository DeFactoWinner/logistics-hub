package com.winner.client.userservice.user.application.command;

import com.winner.client.userservice.user.domain.enums.RoleType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SignupCommand(
    String userName,
    String name,
    String password,
    String phoneNumber,
    String slackId,
    UUID referenceId,
    RoleType role) {

}
