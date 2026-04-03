package com.winner.client.userservice.user.domain.vo;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.userservice.common.exception.UserErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@RequiredArgsConstructor
@Getter
public class PhoneNumber {

  @Column(name = "phone_number", length = 13, nullable = false)
  private String number;

  public PhoneNumber(String number) {
    validate(number);
    this.number = number;
  }

  private static void validate(String phoneNumber) {
    if (phoneNumber == null) {
      throw new BusinessException(UserErrorCode.INVALID_INPUT_VALUE);
    }
    try {
      Long.parseLong(phoneNumber);
    } catch (NumberFormatException e) {
      throw new BusinessException(UserErrorCode.INVALID_INPUT_VALUE);

    }
  }
}
