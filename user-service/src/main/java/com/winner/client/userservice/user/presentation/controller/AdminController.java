package com.winner.client.userservice.user.presentation.controller;

import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.client.userservice.user.application.service.UserQueryService;
import com.winner.client.userservice.user.presentation.dto.response.UserDetailResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminController {

  private final UserQueryService userQueryService;

  @GetMapping("/{userId}")
  public ResponseEntity<ApiResponse<UserDetailResponse>> getUserDetailsForAdmin(
      @PathVariable(name = "userId") UUID userId) {
    return ResponseEntity
        .status(
            CommonSuccessCode.OK.getStatus()
        )
        .body(
            ApiResponse.success(
                CommonSuccessCode.OK,
                "해당 유저를 조회하였습니다.",
                UserDetailResponse.from(
                    userQueryService.getUserDetail(userId)
                )
            )
        );
  }
}
