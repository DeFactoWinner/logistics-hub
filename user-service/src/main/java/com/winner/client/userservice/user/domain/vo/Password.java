package com.winner.client.userservice.user.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

  @Column(name = "password_hash", nullable = false)
  private String value;

  public Password(String encodeValue) {
    this.value = encodeValue;
  }

  public boolean matches(String rawPassword, PasswordEncoder encoder) {
    if (rawPassword == null) {
      return false;
    }
    return encoder.matches(rawPassword, this.value);
  }
}