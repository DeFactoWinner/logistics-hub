package com.winner.client.userservice.user.presentation.controller;

import com.winner.client.global.pagination.CommonPageRequest;
import com.winner.client.global.pagination.PageResponse;
import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.client.global.security.CustomUserPrincipal;
import com.winner.client.userservice.user.application.dto.result.UserSearchResult;
import com.winner.client.userservice.user.application.service.UserQueryService;
import com.winner.client.userservice.user.presentation.dto.request.ManagerUserSearchRequest;
import com.winner.client.userservice.user.presentation.dto.response.UserDetailResponse;
import com.winner.client.userservice.user.presentation.dto.response.UserSearchResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/users")
@RequiredArgsConstructor
public class InternalController {

  private final UserQueryService userQueryService;

  @GetMapping("/{userId}")
  public ResponseEntity<ApiResponse<UserDetailResponse>> getUserDetailsForManager(
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

  @GetMapping()
  public ResponseEntity<ApiResponse<PageResponse<UserSearchResponse>>> getUsersForManager(
      @ModelAttribute ManagerUserSearchRequest searchRequest,
      CommonPageRequest pageRequest,
      @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal) {

    Page<UserSearchResult> resultPage = userQueryService.queryUsersByManage(
        ManagerUserSearchRequest.toQuery(searchRequest, pageRequest,
            customUserPrincipal.referenceId()));
    Page<UserSearchResponse> infoPage = resultPage.map(UserSearchResponse::from);

    return ResponseEntity
        .status(
            CommonSuccessCode.OK.getStatus()
        )
        .body(
            ApiResponse.success(CommonSuccessCode.OK,
                "매니저의 유저 목록을 조회하였습니다.",
                PageResponse.of(
                    infoPage
                )
            )
        );
  }
}
