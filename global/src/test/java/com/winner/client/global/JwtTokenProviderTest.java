package com.winner.client.global;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.winner.client.global.config.jwt.JwtTokenProvider;
import com.winner.client.global.exception.BusinessException;
import io.jsonwebtoken.Claims;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtTokenProviderTest {

  private final Long TEST_ACCESS_TIME = 3600000L;
  private final Long TEST_REFRESH_TIME = 604800000L;
  private JwtTokenProvider jwtTokenProvider;

  @BeforeEach
  void setUp() {
    jwtTokenProvider = new JwtTokenProvider(null);

    String envSecret = System.getenv("JWT_SECRET");

    assertThat(envSecret)
        .as("환경 변수 JWT_SECRET이 설정되지 않았습니다. .env 파일을 확인하세요.")
        .isNotNull();
    ReflectionTestUtils.setField(jwtTokenProvider, "secret", envSecret);
    ReflectionTestUtils.setField(jwtTokenProvider, "accessExpirationTime", TEST_ACCESS_TIME);
    ReflectionTestUtils.setField(jwtTokenProvider, "refreshExpirationTime", TEST_REFRESH_TIME);
    jwtTokenProvider.init();
  }

  @Test
  @DisplayName("AccessToken 생성 및 클레임 추출 성공 테스트")
  void createAccessToken_Success() {
    UUID userId = UUID.randomUUID();
    String role = "ROLE_USER";
    UUID referenceId = UUID.randomUUID();

    String token = jwtTokenProvider.createAccessToken(userId, role, referenceId, false);
    Claims claims = jwtTokenProvider.getClaims(token);

    assertThat(token).isNotNull();
    assertThat(claims.getSubject()).isEqualTo(userId.toString());
    assertThat(claims.get("role")).isEqualTo(role);
    assertThat(claims.get("referenceId")).isEqualTo(referenceId.toString());
  }

  @Test
  @DisplayName("유효한 토큰 검증 성공")
  void validateToken_Success() {
    String token = jwtTokenProvider.createAccessToken(UUID.randomUUID(), "USER", UUID.randomUUID(),
        false);
    boolean isValid = jwtTokenProvider.validateToken(token);
    assertThat(isValid).isTrue();
  }

  @Test
  @DisplayName("만료된 토큰 검증 시 예외 발생")
  void validateToken_Expired() {
    ReflectionTestUtils.setField(jwtTokenProvider, "accessExpirationTime", 0L);
    String expiredToken = jwtTokenProvider.createAccessToken(UUID.randomUUID(), "USER",
        UUID.randomUUID(), false);

    assertThatThrownBy(() -> jwtTokenProvider.validateToken(expiredToken))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  @DisplayName("잘못된 형식의 토큰 검증 시 예외 발생")
  void validateToken_Invalid() {
    String invalidToken = "this.is.not.a.valid.jwt.token";
    assertThatThrownBy(() -> jwtTokenProvider.validateToken(invalidToken))
        .isInstanceOf(BusinessException.class);
  }

  @Test
  @DisplayName("토큰의 남은 유효 시간 계산 성공")
  void getRemainingTime_Success() {
    String token = jwtTokenProvider.createAccessToken(UUID.randomUUID(), "USER", UUID.randomUUID(),
        false);

    long remainingTime = jwtTokenProvider.getRemainingTime(token);

    assertThat(remainingTime).isGreaterThan(0);
    assertThat(remainingTime).isLessThanOrEqualTo(TEST_ACCESS_TIME);
  }
}