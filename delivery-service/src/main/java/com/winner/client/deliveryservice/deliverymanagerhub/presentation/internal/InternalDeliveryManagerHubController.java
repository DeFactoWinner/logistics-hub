package com.winner.client.deliveryservice.deliverymanagerhub.presentation.internal;

import com.winner.client.deliveryservice.deliverymanagerhub.application.DeliveryManagerHubWriteService;
import com.winner.client.deliveryservice.deliverymanagerhub.presentation.dto.requests.DeliveryManagerHubRegistrationRequest;
import com.winner.client.deliveryservice.deliverymanagerhub.presentation.dto.responses.DeliveryManagerHubInfo;
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
@RequestMapping("/internal/v1/delivery-managers/hub")
@RequiredArgsConstructor
public class InternalDeliveryManagerHubController {

	private final DeliveryManagerHubWriteService deliveryManagerHubWriteService;

	@PostMapping
	public ResponseEntity<ApiResponse<DeliveryManagerHubInfo>> registration(
		@RequestBody DeliveryManagerHubRegistrationRequest request
	) {
		return ResponseEntity
			.status(CommonSuccessCode.CREATED.getStatus()).body(
				ApiResponse.success(CommonSuccessCode.CREATED,
					DeliveryManagerHubInfo.from(
					deliveryManagerHubWriteService.registration(
						DeliveryManagerHubRegistrationRequest.toCommand(request.userId())))));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse<Void>> deactivate(
		@PathVariable UUID userId
	) {
		deliveryManagerHubWriteService.deactivate(userId);
		return ResponseEntity.status(CommonSuccessCode.DELETED.getStatus()).body(
			ApiResponse.success(CommonSuccessCode.DELETED, null)
		);
	}
}
