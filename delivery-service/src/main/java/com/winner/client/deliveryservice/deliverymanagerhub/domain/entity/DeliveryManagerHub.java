package com.winner.client.deliveryservice.deliverymanagerhub.domain.entity;

import com.winner.client.deliveryservice.common.constants.DeliveryManagerStatus;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.vo.AssignmentOrder;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.vo.DeliveryId;
import com.winner.client.deliveryservice.deliverymanagerhub.domain.vo.DeliveryManagerUserId;
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
public class DeliveryManagerHub {

	@Getter
	@Id
	@GeneratedValue
	@UuidGenerator
	@Column(name = "id", nullable = false)
	private UUID id;

	@Embedded
	private DeliveryManagerUserId userId;

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

	public static DeliveryManagerHub create(UUID userId, Long assignmentOrder) {
		DeliveryManagerHub manager = new DeliveryManagerHub();
		manager.userId = new DeliveryManagerUserId(userId);
		manager.assignmentOrder = new AssignmentOrder(assignmentOrder);
		manager.deliveryManagerStatus = DeliveryManagerStatus.AVAILABLE;
		manager.deliveryId = new DeliveryId(null);
		return manager;
	}

	public void assignDelivery(UUID deliveryId) {
		if (!this.deliveryManagerStatus.isAvailable()) {
			throw new IllegalStateException("배송 가능 상태가 아닙니다.");
		}
		this.deliveryId = new DeliveryId(deliveryId);
		this.deliveryManagerStatus = DeliveryManagerStatus.IN_DELIVERY;
	}

	public void completeDelivery() {
		this.lastDeliveryCompletedTime = LocalDateTime.now();
		this.deliveryManagerStatus = DeliveryManagerStatus.AVAILABLE;
		this.deliveryId = new DeliveryId(null);
	}

	public UUID getUserId() { return userId.getValue(); }
	public Long getAssignmentOrder() { return assignmentOrder.getValue(); }
	public UUID getDeliveryId() { return deliveryId.getValue(); }
}