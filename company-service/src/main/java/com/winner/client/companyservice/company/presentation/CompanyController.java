package com.winner.client.companyservice.company.presentation;

import com.winner.client.companyservice.company.application.CompanyService;
import com.winner.client.companyservice.company.application.dto.CompanyServiceDto;
import com.winner.client.companyservice.company.presentation.dto.CompanyRequestDto;
import com.winner.client.companyservice.company.presentation.dto.CompanyResponseDto;
import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.SuccessCode;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {

  private final CompanyService companyService;

  @PostMapping
  public ResponseEntity<ApiResponse<CompanyResponseDto>> createCompany(@RequestBody CompanyRequestDto.create companyRequestDto) {

    CompanyServiceDto.create serviceDto = companyRequestDto.toServiceDto();

    CompanyResponseDto result = companyService.createCompany(serviceDto);

    return ResponseEntity.ok(ApiResponse.success(SuccessCode.CREATED,result));
  }

  @PatchMapping("/{companyId}")
  public ResponseEntity<ApiResponse<CompanyResponseDto>> updateCompany(@RequestBody CompanyRequestDto.update companyRequestDto,
      @PathVariable UUID companyId) {

    CompanyServiceDto.update serviceDto = companyRequestDto.toServiceDto();

    CompanyResponseDto result = companyService.updateCompany(companyId,serviceDto);

    return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK,result));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CompanyResponseDto>>> getCompany() {

    List<CompanyResponseDto> result = companyService.selectCompanyList();

    return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK,result));

  }

  @GetMapping("/{companyId}")
  public ResponseEntity<ApiResponse<CompanyResponseDto>> companyDetail(@PathVariable UUID companyId) {

    CompanyResponseDto result = companyService.selectCompany(companyId);

    return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK,result));
  }


  @DeleteMapping("/{companyId}")
  public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable UUID companyId) {

    companyService.deleteCompany(companyId);

    return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK,null));
  }

}
