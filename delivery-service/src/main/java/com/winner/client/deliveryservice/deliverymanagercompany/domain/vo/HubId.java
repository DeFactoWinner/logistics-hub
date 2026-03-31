package com.winner.client.deliveryservice.deliverymanagercompany.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class HubId {

	@Column(name = "hub_id", nullable = false)
	private UUID value;

	public HubId(UUID value) {
		this.value = value;
	}
}
