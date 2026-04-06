package com.winner.client.companyservice.company.presentation;

import com.winner.client.companyservice.company.application.service.CompanyCommandService;
import com.winner.client.companyservice.company.application.service.CompanyQueryService;
import com.winner.client.companyservice.company.presentation.dto.request.CreateCompanyRequest;
import com.winner.client.companyservice.company.presentation.dto.request.UpdateCompanyRequest;
import com.winner.client.companyservice.company.presentation.dto.response.CompanyResponse;
import com.winner.client.companyservice.company.presentation.dto.response.ListCompanyResponse;
import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.client.global.security.CustomUserPrincipal;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/companies")
public class CompanyController {

  private final CompanyCommandService companyCommandService;
  private final CompanyQueryService companyQueryService;

  @PostMapping
  @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
  public ResponseEntity<ApiResponse<CompanyResponse>> createCompany(
      @Valid @RequestBody CreateCompanyRequest companyRequestDto,
      @AuthenticationPrincipal CustomUserPrincipal principal) {

    CompanyResponse result = companyCommandService.createCompany(companyRequestDto,principal);

    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.CREATED,result));
  }

  @PatchMapping("/{companyId}")
  @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
  public ResponseEntity<ApiResponse<CompanyResponse>> updateCompany(
      @RequestBody UpdateCompanyRequest companyRequestDto,
      @PathVariable UUID companyId,
      @AuthenticationPrincipal CustomUserPrincipal principal) {

    CompanyResponse result = companyCommandService.updateCompany(companyId,companyRequestDto,principal);

    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,result));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<ListCompanyResponse>> getCompanyList() {
    ListCompanyResponse result = companyQueryService.getCompanyList();

    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,result));
  }

  @GetMapping("/{companyId}")
  public ResponseEntity<ApiResponse<CompanyResponse>> getCompany(@PathVariable UUID companyId) {
    CompanyResponse result = companyQueryService.getCompany(companyId);
    log.info("companyId : {}",result);
    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,result));
  }

  @DeleteMapping("/{companyId}")
  @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
  public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable UUID companyId,
      @AuthenticationPrincipal CustomUserPrincipal principal) {

    companyCommandService.deleteCompany(companyId,principal);

    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,null));
  }

  @GetMapping("/search")
  public ResponseEntity<ApiResponse<ListCompanyResponse>> getCompanyByName(@RequestParam String companyName) {

    ListCompanyResponse result = companyQueryService.getCompanyListByCompanyName(companyName);

    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,result));
  }

}
