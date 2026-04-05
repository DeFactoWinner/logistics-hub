package com.winner.client.deliveryservice.deliverymanagerhub.domain.entity;

import static com.winner.client.deliveryservice.common.exception.deliverymanager.hub.DeliveryManagerHubErrorCode.DELIVERY_MANAGER_IN_PROGRESS;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.hub.DeliveryManagerHubErrorCode.HUB_DELIVERY_MANAGER_OVER_CAPACITY;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.hub.DeliveryManagerHubErrorCode.NOT_AVAILABLE;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.hub.DeliveryManagerHubErrorCode.USER_ID_CANNOT_BE_NULL;

import com.winner.client.deliveryservice.common.constants.DeliveryManagerStatus;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.vo.AssignmentOrder;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.vo.DeliveryId;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.vo.DeliveryManagerUser;
import com.winner.client.global.entity.BaseAuditEntity;
import com.winner.client.global.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "p_delivery_manager_hub")
public class DeliveryManagerHub extends BaseAuditEntity {

	@Getter
	@Id
	@GeneratedValue
	@UuidGenerator
	@Column(name = "id", nullable = false)
	private UUID id;

	@Embedded
	private DeliveryManagerUser user;

	@Embedded
	private AssignmentOrder assignmentOrder;

	@Embedded
	private DeliveryId deliveryId;

	@Getter
	@Enumerated(EnumType.STRING)
	@Column(name = "delivery_manager_status", nullable = false)
	private DeliveryManagerStatus deliveryManagerStatus;

	@Getter
	@Column(name = "last_delivery_completed_time")
	private LocalDateTime lastDeliveryCompletedTime;


	protected DeliveryManagerHub() {}

	public static DeliveryManagerHub create(UUID userId, String name, Long assignmentOrder, Long curCount) {
		if (curCount >= 10) {
			throw new BusinessException(HUB_DELIVERY_MANAGER_OVER_CAPACITY);
		}
		DeliveryManagerHub manager = new DeliveryManagerHub();
		manager.user = new DeliveryManagerUser(userId, name);
		manager.assignmentOrder = new AssignmentOrder(assignmentOrder);
		manager.deliveryManagerStatus = DeliveryManagerStatus.AVAILABLE;
		manager.deliveryId = new DeliveryId(null);
		return manager;
	}

	public void assignDelivery(UUID deliveryId) {
		if (!this.deliveryManagerStatus.isAvailable()) {
			throw new BusinessException(NOT_AVAILABLE);
		}
		this.deliveryId = new DeliveryId(deliveryId);
		this.deliveryManagerStatus = DeliveryManagerStatus.IN_DELIVERY;
	}

	public void completeDelivery() {
		this.lastDeliveryCompletedTime = LocalDateTime.now();
		this.deliveryManagerStatus = DeliveryManagerStatus.AVAILABLE;
		this.deliveryId = new DeliveryId(null);
	}

	public void switchStatus() {
		switch (this.deliveryManagerStatus) {
			case AVAILABLE -> this.deliveryManagerStatus = DeliveryManagerStatus.OFF_DUTY;
			case OFF_DUTY -> this.deliveryManagerStatus = DeliveryManagerStatus.AVAILABLE;
		}
	}

	@Override
	public void softDelete(UUID userId) {
		if (this.deliveryManagerStatus == DeliveryManagerStatus.IN_DELIVERY) {
			throw new BusinessException(DELIVERY_MANAGER_IN_PROGRESS);
		}
		super.softDelete(userId);
	}

	public UUID getUserId() { return user.getUserId(); }
	public String getName() { return user.getName(); }
	public Long getAssignmentOrder() { return assignmentOrder.getValue(); }
	public UUID getDeliveryId() {
		return deliveryId != null ? deliveryId.getValue() : new DeliveryId(null).getValue();
	}
}