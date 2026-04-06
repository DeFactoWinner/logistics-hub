package com.winner.client.userservice.user.presentation.controller;

import com.winner.client.global.pagination.CommonPageRequest;
import com.winner.client.global.pagination.PageResponse;
import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.client.userservice.user.application.dto.result.UserSearchResult;
import com.winner.client.userservice.user.application.service.UserCommandService;
import com.winner.client.userservice.user.application.service.UserQueryService;
import com.winner.client.userservice.user.presentation.dto.request.AdminUserSearchRequest;
import com.winner.client.userservice.user.presentation.dto.request.UserPatchRequest;
import com.winner.client.userservice.user.presentation.dto.response.UserDetailResponse;
import com.winner.client.userservice.user.presentation.dto.response.UserSearchResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminController {

  private final UserQueryService userQueryService;
  private final UserCommandService userCommandService;

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

  @PatchMapping("/{userId}")
  public ResponseEntity<ApiResponse<UserDetailResponse>> updateUserForAdmin(
      @PathVariable(name = "userId") UUID userId,
      @Valid @RequestBody UserPatchRequest request) {
    return ResponseEntity
        .status(
            CommonSuccessCode.OK.getStatus()
        )
        .body(
            ApiResponse.success(
                CommonSuccessCode.OK,
                "해당 유저 정보를 수정하였습니다.",
                UserDetailResponse.from(
                    userCommandService
                        .updateUser(UserPatchRequest.toCommand(userId, request))
                )
            )
        );
  }

  @GetMapping()
  public ResponseEntity<ApiResponse<PageResponse<UserSearchResponse>>> getUsersForAdmin(
      @ModelAttribute AdminUserSearchRequest searchRequest,
      CommonPageRequest pageRequest) {

    Page<UserSearchResult> resultPage = userQueryService.queryUsersByAdmin(
        AdminUserSearchRequest.toQuery(searchRequest, pageRequest));
    Page<UserSearchResponse> infoPage = resultPage.map(UserSearchResponse::from);

    return ResponseEntity
        .status(
            CommonSuccessCode.OK.getStatus()
        )
        .body(
            ApiResponse.success(CommonSuccessCode.OK,
                "유저 목록을 조회하였습니다.",
                PageResponse.of(infoPage)
            )
        );
  }
}
