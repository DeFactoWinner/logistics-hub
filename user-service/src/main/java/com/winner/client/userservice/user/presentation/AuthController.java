package com.winner.client.userservice.user.presentation;

import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.client.userservice.user.application.service.AuthCommandService;
import com.winner.client.userservice.user.presentation.request.SignupRequest;
import com.winner.client.userservice.user.presentation.response.SignupResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthCommandService authCommandService;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<SignupResponse>> signup(
      @Valid @RequestBody SignupRequest signupRequest) {
    return ResponseEntity
        .status(
            CommonSuccessCode.CREATED.getStatus())
        .body(
            ApiResponse.success(
                CommonSuccessCode.CREATED,
                SignupResponse.from(
                    authCommandService.signup(SignupRequest.toCommand(signupRequest))
                )
            )
        );
  }
}