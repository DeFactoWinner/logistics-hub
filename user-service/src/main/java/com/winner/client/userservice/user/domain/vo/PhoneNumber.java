package com.winner.client.userservice.user.domain.vo;

import com.winner.client.userservice.common.exception.BusinessException;
import com.winner.client.userservice.common.exception.UserErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@RequiredArgsConstructor
@Getter
public class PhoneNumber {

  private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\d+$");
  @Column(name = "phone_number", length = 13, nullable = false)
  private String number;

  public PhoneNumber(String number) {
    validate(number);
    this.number = number;
  }

  private static void validate(String phoneNumber) {
    if (phoneNumber == null || !PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
      throw new BusinessException(UserErrorCode.INVALID_INPUT_VALUE);
    }
    try {
      Long.parseLong(phoneNumber);
    } catch (NumberFormatException e) {
      throw new BusinessException(UserErrorCode.INVALID_INPUT_VALUE);

    }
  }
}
