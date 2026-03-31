package com.winner.client.companyservice.presentation;

import com.winner.client.companyservice.application.CompanyService;
import com.winner.client.companyservice.application.dto.CompanyServiceDto;
import com.winner.client.companyservice.presentation.dto.CompanyRequestDto;
import com.winner.client.companyservice.presentation.dto.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
  public CompanyResponseDto createCompany(@RequestBody CompanyRequestDto.create companyRequestDto) {

    CompanyServiceDto.create serviceDto = companyRequestDto.toServiceDto();

    return companyService.createCompany(serviceDto);
  }

  @GetMapping
  public String getCompany() {
    return "업체 전체 조회";
  }

  @GetMapping("/{companyId}")
  public String companyDetail() {
    return "업체 단건 조회";
  }

  @PatchMapping("/{companyId}")
  public String updateCompany() {
    return "업체 수정";
  }

  @DeleteMapping("/{companyId}")
  public String deleteCompany() {
    return "업체 삭제";
  }

}
