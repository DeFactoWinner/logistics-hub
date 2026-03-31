package com.winner.client.deliveryservice.deliverymanagercompany.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class AssignmentOrder {

	@Column(name = "assignment_order", nullable = false)
	private Long value;

	public AssignmentOrder(Long value) {
		if (value == null || value < 0) {
			throw new IllegalArgumentException("value must be greater than or equal to 0");
		}
		this.value = value;
	}

}
