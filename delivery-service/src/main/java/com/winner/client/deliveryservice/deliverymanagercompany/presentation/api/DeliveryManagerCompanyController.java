package com.winner.client.deliveryservice.deliverymanagercompany.presentation.api;

import static com.winner.client.global.response.CommonSuccessCode.OK;

import com.winner.client.deliveryservice.deliverymanagercompany.application.DeliveryManagerCompanyReadService;
import com.winner.client.deliveryservice.deliverymanagercompany.presentation.responses.DeliveryManagerCompanyInfo;
import com.winner.client.global.response.ApiResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery-managers/company")
public class DeliveryManagerCompanyController {

	private final DeliveryManagerCompanyReadService deliveryManagerCompanyReadService;

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<DeliveryManagerCompanyInfo>> getDeliveryManagerCompany(
		@PathVariable UUID id
	) {
		return ResponseEntity.ok()
			.body(ApiResponse.success(
				OK,
				DeliveryManagerCompanyInfo.from(
					deliveryManagerCompanyReadService.getDetail(id)))
			);
	}

}
