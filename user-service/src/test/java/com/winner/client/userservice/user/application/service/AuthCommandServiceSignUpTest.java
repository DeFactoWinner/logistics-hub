package com.winner.client.userservice.user.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.userservice.common.exception.UserErrorCode;
import com.winner.client.userservice.user.application.command.SignupCommand;
import com.winner.client.userservice.user.application.result.SignupResult;
import com.winner.client.userservice.user.application.service.impl.AuthCommandServiceImpl;
import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.enums.RoleType;
import com.winner.client.userservice.user.domain.repository.UserRepository;
import com.winner.client.userservice.user.domain.vo.Password;
import com.winner.client.userservice.user.domain.vo.PhoneNumber;
import com.winner.client.userservice.user.domain.vo.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class) // Mockito 환경 설정
class AuthCommandServiceSignUpTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthCommandServiceImpl authCommandService;

  private SignupCommand signupCommand;

  @BeforeEach
  void setUp() {
    signupCommand = new SignupCommand(
        "0000yuyu",
        "유유",
        "password123!",
        "01012345678",
        "slackId-00000",
        null,
        RoleType.DELIVERY_MANAGER
    );
  }

  @Test
  @DisplayName("회원가입 성공")
  void signup_success() {
    given(userRepository.existsByUsername(anyString())).willReturn(false);
    given(passwordEncoder.encode(anyString())).willReturn("encoded_hash_value");

    User mockUser = User.create(
        signupCommand.username(),
        signupCommand.name(),
        new Password("encoded_hash_value"),
        new PhoneNumber(signupCommand.phoneNumber()),
        signupCommand.slackId(),
        new UserRole(signupCommand.role(), signupCommand.referenceId())
    );
    given(userRepository.save(any(User.class))).willReturn(mockUser);

    SignupResult result = authCommandService.signup(signupCommand);

    assertThat(result).isNotNull();
    verify(userRepository, times(1)).save(any(User.class));
    verify(passwordEncoder, times(1)).encode(signupCommand.password());
  }

  @Test
  @DisplayName("회원가입 실패 - 중복된 아이디인 경우 BusinessException 발생")
  void signup_fail_duplicate_username() {
    // given
    given(userRepository.existsByUsername(signupCommand.username())).willReturn(true);

    assertThatThrownBy(() -> authCommandService.signup(signupCommand))
        .isInstanceOf(BusinessException.class)
        .hasMessageContaining(UserErrorCode.DUPLICATE_USERNAME.getMessage());

    verify(userRepository, never()).save(any(User.class));
  }
}