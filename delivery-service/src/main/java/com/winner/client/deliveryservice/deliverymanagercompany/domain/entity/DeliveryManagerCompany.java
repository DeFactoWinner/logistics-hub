package com.winner.client.deliveryservice.deliverymanagercompany.domain.entity;

import static com.winner.client.deliveryservice.common.exception.deliverymanager.company.DeliveryManagerCompanyErrorCode.DELIVERY_ID_MISMATCH;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.company.DeliveryManagerCompanyErrorCode.DELIVERY_MANAGER_IN_PROGRESS;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.company.DeliveryManagerCompanyErrorCode.DELIVERY_NOT_FOUND;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.company.DeliveryManagerCompanyErrorCode.EXCEEDED_TO_IN_HUB_DELIVERY_MANAGER;
import static com.winner.client.deliveryservice.common.exception.deliverymanager.company.DeliveryManagerCompanyErrorCode.NOT_AVAILABLE;

import com.winner.client.deliveryservice.common.constants.DeliveryManagerStatus;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.vo.AssignmentOrder;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.vo.DeliveryId;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.vo.DeliveryManagerUser;
import com.winner.client.deliveryservice.deliverymanagercompany.domain.vo.HubId;
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
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "p_delivery_manager_company")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class DeliveryManagerCompany extends BaseAuditEntity {

	@Getter
	@Id
	@GeneratedValue
	@UuidGenerator
	@Column(name = "id", nullable = false)
	private UUID id;

	@Embedded
	private DeliveryManagerUser user;

	@Embedded
	private HubId hubId;

	@Embedded
	private DeliveryId deliveryId;

	@Embedded
	private AssignmentOrder assignmentOrder;

	@Getter
	@Enumerated(EnumType.STRING)
	@Column(name = "delivery_manager_status", nullable = false)
	private DeliveryManagerStatus deliveryManagerStatus;

	@Getter
	@Column(name = "last_delivery_completed_time")
	private LocalDateTime lastDeliveryCompletedTime;

	public static DeliveryManagerCompany create(UUID userId, String name, UUID hubId, Long assignmentOrder, Long curCount) {
		if (curCount >= 10) {
			throw new BusinessException(EXCEEDED_TO_IN_HUB_DELIVERY_MANAGER);
		}
		DeliveryManagerCompany manager = new DeliveryManagerCompany();
		manager.user = new DeliveryManagerUser(userId, name);
		manager.hubId = new HubId(hubId);
		manager.deliveryId = new DeliveryId(null);
		manager.assignmentOrder = new AssignmentOrder(assignmentOrder);
		manager.deliveryManagerStatus = DeliveryManagerStatus.AVAILABLE;
		return manager;
	}

	public void assignDelivery(UUID deliveryId) {
		if (!this.deliveryManagerStatus.isAvailable()) {
			throw new BusinessException(NOT_AVAILABLE);
		}
		this.deliveryId = new DeliveryId(deliveryId);
		this.deliveryManagerStatus = DeliveryManagerStatus.IN_DELIVERY;
	}

	public void completeDelivery(UUID deliveryId) {
		if (!getNullSafeDeliveryId().isAssigned()) {
			throw new BusinessException(DELIVERY_NOT_FOUND);
		}
		if (!this.deliveryId.isMatched(deliveryId)) {
			throw new BusinessException(DELIVERY_ID_MISMATCH);
		}
		this.lastDeliveryCompletedTime = LocalDateTime.now();
		this.deliveryManagerStatus = DeliveryManagerStatus.AVAILABLE;
		this.deliveryId = new DeliveryId(null);
	}

	public void changeHub(UUID newHubId) {
		this.hubId = new HubId(newHubId);
	}

	public void switchStatus() {
		switch (this.deliveryManagerStatus) {
			case AVAILABLE -> this.deliveryManagerStatus = DeliveryManagerStatus.OFF_DUTY;
			case OFF_DUTY -> this.deliveryManagerStatus = DeliveryManagerStatus.AVAILABLE;
		}
	}

	@Override
	public void softDelete(UUID userId) {
		if (this.deliveryManagerStatus ==  DeliveryManagerStatus.IN_DELIVERY) {
			throw new BusinessException(DELIVERY_MANAGER_IN_PROGRESS);
		}
		super.softDelete(userId);
	}

	public UUID getUser() { return user.getUserId(); }
	public String getName() { return user.getName(); }
	public UUID getHubId() { return hubId.getValue(); }
	public UUID getDeliveryId() {
		return getNullSafeDeliveryId().getValue();
	}
	public Long getAssignmentOrder() { return assignmentOrder.getValue(); }

	private DeliveryId getNullSafeDeliveryId() {
		return deliveryId != null ? this.deliveryId : new DeliveryId(null);
	}
}