package com.winner.client.userservice.user.domain.vo;

import com.winner.client.userservice.common.exception.BusinessException;
import com.winner.client.userservice.common.exception.UserErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Password {

  @NotBlank(message = "비밀번호는 필수 값입니다.")
  @Column(name = "password_hash", length = 255, nullable = false)
  private String value;

  public static Password encode(final String rawPassword, final PasswordEncoder encoder) {
    validatePassword(rawPassword);
    return new Password(encoder.encode(rawPassword));
  }

  private static void validatePassword(final String rawPassword) {
    if (Objects.isNull(rawPassword)) {
      throw new BusinessException(UserErrorCode.INVALID_INPUT_VALUE);
    }
    if (rawPassword.length() < 8) {
      throw new BusinessException(UserErrorCode.INVALID_INPUT_VALUE);
    }
  }

  public boolean matches(String rawPassword, PasswordEncoder encoder) {
    return encoder.matches(rawPassword, this.value);
  }
}
