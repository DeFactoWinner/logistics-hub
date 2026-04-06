package com.winner.client.userservice.user.presentation.dto.request;

import com.winner.client.userservice.user.application.dto.command.SignupCommand;
import com.winner.client.userservice.user.domain.enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import org.hibernate.validator.constraints.Length;

public record SignupRequest(
    @Length(min = 4, max = 10)
    @NotBlank(message = "아이디는 필수입니다.")
    String userName,
    @Length(min = 8, max = 15)
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).{8,}$",
        message = "비밀번호는 8자 이상, 문자/숫자/특수문자를 각각 하나 이상 포함해야 합니다."
    )
    @NotBlank(message = "비밀번호는 필수입니다.")
    String password,
    @NotBlank(message = "이름은 필수입니다.")
    String name,
    @Length(max = 13)
    @Pattern(regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$")
    @NotBlank(message = "전화번호는 필수입니다.")
    String phoneNumber,
    @Length(max = 255)
    String slackId,
    UUID referenceId,
    @NotNull(message = "역할은 필수 입니다.")
    RoleType role) {

  public static SignupCommand toCommand(SignupRequest request) {
    return SignupCommand.builder()
        .name(request.name)
        .password(request.password)
        .phoneNumber(request.phoneNumber)
        .referenceId(request.referenceId)
        .role(request.role)
        .slackId(request.slackId)
        .userName(request.userName)
        .build();
  }
}
