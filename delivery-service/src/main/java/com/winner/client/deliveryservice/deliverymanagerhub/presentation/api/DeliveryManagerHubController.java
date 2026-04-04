package com.winner.client.deliveryservice.deliverymanagerhub.presentation.api;

import static com.winner.client.global.response.CommonSuccessCode.OK;

import com.winner.client.deliveryservice.deliverymanagerhub.application.DeliveryManagerHubReadService;
import com.winner.client.deliveryservice.deliverymanagerhub.application.DeliveryManagerHubWriteService;
import com.winner.client.deliveryservice.deliverymanagerhub.presentation.dto.responses.DeliveryManagerHubInfo;
import com.winner.client.global.response.ApiResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery-managers/hub")
public class DeliveryManagerHubController {

	private final DeliveryManagerHubWriteService deliveryManagerHubWriteService;
	private final DeliveryManagerHubReadService deliveryManagerHubReadService;

	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse<DeliveryManagerHubInfo>> getDeliveryManagerHub(
		@PathVariable UUID userId
	) {
		return ResponseEntity.ok()
			.body(ApiResponse.success(OK,
				DeliveryManagerHubInfo.from(deliveryManagerHubReadService.getDetail(userId))));
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<ApiResponse<DeliveryManagerHubInfo>> switchStatus(
		@PathVariable UUID userId
	) {
		return ResponseEntity.ok().body(ApiResponse.success(OK,
			DeliveryManagerHubInfo.from(deliveryManagerHubWriteService.switchStatus(userId))));
	}
}
