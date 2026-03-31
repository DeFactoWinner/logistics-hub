package com.winner.client.deliveryservice.deliverymanagercompany.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class DeliveryManagerUserId {

	@Column(name = "delivery_manager_id", nullable = false)
	private UUID value;

	public DeliveryManagerUserId(UUID value) {
		if (value == null) {
			throw new IllegalArgumentException("value cannot be null");
		}
		this.value = value;
	}
}
