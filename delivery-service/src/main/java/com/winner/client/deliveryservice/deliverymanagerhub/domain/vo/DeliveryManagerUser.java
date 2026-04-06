package com.winner.client.deliveryservice.deliverymanagerhub.domain.vo;

import static com.winner.client.deliveryservice.common.exception.deliverymanager.hub.DeliveryManagerHubErrorCode.USER_ID_CANNOT_BE_NULL;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.hub.DeliveryManagerHubErrorCode.USER_NAME_CANNOT_BE_NULL;

import com.winner.client.global.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class DeliveryManagerUser {

	@Column(name = "user_id", nullable = false)
	private UUID userId;

	@Column(name = "name")
	private String name;

	public DeliveryManagerUser(UUID userId, String name) {
		if (userId == null) {
			throw new BusinessException(USER_ID_CANNOT_BE_NULL);
		}
		if (name == null) {
			throw new BusinessException(USER_NAME_CANNOT_BE_NULL);
		}
 		this.userId = userId;
		this.name = name;
	}
}
