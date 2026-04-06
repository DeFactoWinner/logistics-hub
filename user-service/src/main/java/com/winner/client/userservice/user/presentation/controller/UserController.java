package com.winner.client.userservice.user.presentation.controller;

import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.client.global.security.CustomUserPrincipal;
import com.winner.client.userservice.user.application.service.UserCommandService;
import com.winner.client.userservice.user.application.service.UserQueryService;
import com.winner.client.userservice.user.presentation.dto.request.UserPatchRequest;
import com.winner.client.userservice.user.presentation.dto.response.UserDetailResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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

  @PatchMapping("/me")
  public ResponseEntity<ApiResponse<UserDetailResponse>> updateUser
      (@AuthenticationPrincipal CustomUserPrincipal userPrincipal,
          @Valid @RequestBody UserPatchRequest request) {
    return ResponseEntity
        .status(
            CommonSuccessCode.OK.getStatus()
        )
        .body(
            ApiResponse.success(
                CommonSuccessCode.OK,
                "내 정보를 수정하였습니다.",
                UserDetailResponse.from(
                    userCommandService.updateUser(
                        UserPatchRequest.toCommand(userPrincipal.userId(), request))
                )
            )
        );
  }

  @DeleteMapping("/me")
  public ResponseEntity<ApiResponse<Void>> deleteUser(
      @AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
    return ResponseEntity
        .status(
            CommonSuccessCode.OK.getStatus()
        )
        .body(
            ApiResponse.success(
                CommonSuccessCode.OK,
                "정상적으로 회원탈퇴 되었습니다.",
                userCommandService.deleteUser(userPrincipal.userId())
            )
        );
  }

}
