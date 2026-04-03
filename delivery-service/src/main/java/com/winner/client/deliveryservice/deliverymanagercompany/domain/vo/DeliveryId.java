package com.winner.client.deliveryservice.deliverymanagercompany.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class DeliveryId {

	@Column(name = "delivery_id")
	private UUID value;

	public DeliveryId(UUID value) {
		this.value = value;
	}

	public boolean isAssigned() {
		return value != null;
	}
}
