package com.winner.client.deliveryservice.deliverymanagercompany.presentation.internal;

import static com.winner.client.global.response.CommonSuccessCode.DELETED;

import com.winner.client.deliveryservice.deliverymanagercompany.application.DeliveryManagerCompanyWriteService;
import com.winner.client.deliveryservice.deliverymanagercompany.presentation.dto.requests.DeliveryManagerCompanyRegistrationRequest;
import com.winner.client.deliveryservice.deliverymanagercompany.presentation.dto.responses.DeliveryManagerCompanyInfo;
import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
						request.toCommand()))));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse<Void>> deactivate(
		@PathVariable UUID userId
	) {
		deliveryManagerCompanyWriteService.deactivate(userId);
		return ResponseEntity.status(DELETED.getStatus()).body(
			ApiResponse.success(DELETED, null)
		);
	}
}
