package com.winner.client.userservice.user.presentation;

import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.client.global.security.CustomUserPrincipal;
import com.winner.client.userservice.user.application.service.UserCommandService;
import com.winner.client.userservice.user.application.service.UserQueryService;
import com.winner.client.userservice.user.presentation.response.UserDetailResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserCommandService userCommandService;
  private final UserQueryService userQueryService;

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> login
      (@AuthenticationPrincipal CustomUserPrincipal userPrincipal, HttpServletRequest request) {

    String accessToken = request.getHeader("Authorization").substring(7);

    return ResponseEntity
        .status(
            CommonSuccessCode.OK.getStatus()
        )
        .body(
            ApiResponse.success(
                CommonSuccessCode.OK,
                "로그아웃 되었습니다.",
                userCommandService.logout(userPrincipal.userId(), accessToken)
            )
        );
  }

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<UserDetailResponse>> getUserDetail
      (@AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
    return ResponseEntity
        .status(
            CommonSuccessCode.OK.getStatus()
        )
        .body(
            ApiResponse.success(
                CommonSuccessCode.OK,
                "내 정보를 조회하였습니다.",
                UserDetailResponse.from(
                    userQueryService.getUserDetail(userPrincipal.userId())
                )
            )
        );
  }
}
