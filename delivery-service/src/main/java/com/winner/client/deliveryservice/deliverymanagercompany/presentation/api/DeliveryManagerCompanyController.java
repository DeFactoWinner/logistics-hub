package com.winner.client.deliveryservice.deliverymanagercompany.presentation.api;

import static com.winner.client.global.response.CommonSuccessCode.OK;

import com.winner.client.deliveryservice.deliverymanagercompany.application.DeliveryManagerCompanyReadService;
import com.winner.client.deliveryservice.deliverymanagercompany.application.DeliveryManagerCompanyWriteService;
import com.winner.client.deliveryservice.deliverymanagercompany.presentation.dto.responses.DeliveryManagerCompanyInfo;
import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.security.CustomUserPrincipal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery-managers/company")
public class DeliveryManagerCompanyController {

	private final DeliveryManagerCompanyReadService deliveryManagerCompanyReadService;
	private final DeliveryManagerCompanyWriteService deliveryManagerCompanyWriteService;

	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse<DeliveryManagerCompanyInfo>> getDeliveryManagerCompany(
		@PathVariable UUID userId
	) {
		return ResponseEntity.ok().body(ApiResponse.success(OK,
			DeliveryManagerCompanyInfo.from(deliveryManagerCompanyReadService.getDetail(userId))));
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<ApiResponse<DeliveryManagerCompanyInfo>> switchStatus(
		@PathVariable UUID userId
	) {
		return ResponseEntity.ok().body(ApiResponse.success(OK,
			DeliveryManagerCompanyInfo.from(deliveryManagerCompanyWriteService.switchStatus(userId))));
	}

	@PatchMapping("/{deliveryId}/completion")
	public ResponseEntity<ApiResponse<Void>> completionDelivery(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@PathVariable UUID deliveryId
	) {
		deliveryManagerCompanyWriteService.completion(principal.userId(), deliveryId);
		return ResponseEntity.status(OK.getStatus())
			.body(ApiResponse.success(OK, null));
	}
}
