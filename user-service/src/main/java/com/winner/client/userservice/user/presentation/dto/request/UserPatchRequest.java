package com.winner.client.userservice.user.presentation.dto.request;

import com.winner.client.userservice.user.application.dto.command.UserPatchCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record UserPatchRequest(

    @Length(max = 20)
    String name,
    @Length(max = 13)
    @Pattern(regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$")
    @NotBlank(message = "전화번호는 필수입니다.")
    String phoneNumber,
    @Length(max = 255)
    String slackId
) {

  public static UserPatchCommand toCommand(UserPatchRequest request) {
    return UserPatchCommand.builder()
        .name(request.name)
        .phoneNumber(request.phoneNumber)
        .slackId(request.slackId)
        .build();
  }
}
