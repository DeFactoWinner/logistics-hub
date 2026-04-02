package com.winner.client.deliveryservice.deliverymanagercompany.presentation.internal;

import com.winner.client.deliveryservice.deliverymanagercompany.application.DeliveryManagerCompanyWriteService;
import com.winner.client.deliveryservice.deliverymanagercompany.presentation.requests.DeliveryManagerCompanyRegistrationRequest;
import com.winner.client.deliveryservice.deliverymanagercompany.presentation.responses.DeliveryManagerCompanyInfo;
import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/delivery-managers/company")
public class InternalDeliveryManagerCompanyController {

	private final DeliveryManagerCompanyWriteService deliveryManagerCompanyWriteService;

	@PostMapping
	public ResponseEntity<ApiResponse<DeliveryManagerCompanyInfo>> registration(
		@RequestBody DeliveryManagerCompanyRegistrationRequest request
	) {
		return ResponseEntity
			.status(CommonSuccessCode.CREATED.getStatus()).body(
				ApiResponse.success(CommonSuccessCode.CREATED,
					DeliveryManagerCompanyInfo.from(
					deliveryManagerCompanyWriteService.registration(
						DeliveryManagerCompanyRegistrationRequest.toCommand(request.userId(),
							request.hubId())))));
	}
}
