package com.winner.client.deliveryservice.deliverymanagercompany.domain.entity;

import com.winner.client.deliveryservice.common.constants.DeliveryManagerStatus;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.vo.AssignmentOrder;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.vo.DeliveryManagerUserId;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.vo.HubId;
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
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "p_delivery_manager_company")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class DeliveryManagerCompany {

	@Getter
	@Id
	@GeneratedValue
	@UuidGenerator
	@Column(name = "id", nullable = false)
	private UUID id;

	@Embedded
	private DeliveryManagerUserId userId;

	@Embedded
	private HubId hubId;

	@Embedded
	private AssignmentOrder assignmentOrder;

	@Getter
	@Enumerated(EnumType.STRING)
	@Column(name = "delivery_manager_status", nullable = false)
	private DeliveryManagerStatus deliveryManagerStatus;

	@Getter
	@Column(name = "last_delivery_completed_time")
	private LocalDateTime lastDeliveryCompletedTime;

	public static DeliveryManagerCompany create(UUID userId, UUID hubId, Long assignmentOrder) {
		DeliveryManagerCompany manager = new DeliveryManagerCompany();
		manager.userId = new DeliveryManagerUserId(userId);
		manager.hubId = new HubId(hubId);
		manager.assignmentOrder = new AssignmentOrder(assignmentOrder);
		manager.deliveryManagerStatus = DeliveryManagerStatus.AVAILABLE;
		return manager;
	}

	public void assignDelivery(UUID deliveryId) {
		if (!this.deliveryManagerStatus.isAvailable()) {
			throw new IllegalStateException("배송 가능 상태가 아닙니다.");
		}
		this.deliveryManagerStatus = DeliveryManagerStatus.IN_DELIVERY;
	}

	public void completeDelivery() {
		this.lastDeliveryCompletedTime = LocalDateTime.now();
		this.deliveryManagerStatus = DeliveryManagerStatus.AVAILABLE;
	}

	public void changeHub(UUID newHubId) {
		this.hubId = new HubId(newHubId);
	}

	public UUID getUserId() { return userId.getValue(); }
	public UUID getHubId() { return hubId.getValue(); }
	public Long getAssignmentOrder() { return assignmentOrder.getValue(); }
}