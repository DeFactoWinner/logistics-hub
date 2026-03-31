package com.winner.client.deliveryservice.deliverymanagerhub.domain.vo;

import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class DeliveryManagerUserId {
	private UUID value;

	public DeliveryManagerUserId(UUID value) {
		if (value == null) {
			throw new IllegalArgumentException("value cannot be null");
		}
		this.value = value;
	}
}
