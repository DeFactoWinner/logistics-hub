package com.winner.client.deliveryservice.delivery.domain.entity;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryRouteStatus;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryStatus;
import com.winner.client.deliveryservice.delivery.domain.vo.Address;
import com.winner.client.deliveryservice.delivery.domain.vo.HubRoute;
import com.winner.client.deliveryservice.delivery.domain.vo.Location;
import com.winner.client.deliveryservice.delivery.domain.vo.Receiver;
import com.winner.client.global.entity.BaseAuditEntity;
import com.winner.client.global.exception.BusinessException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(
    name = "p_delivery",
    uniqueConstraints = @UniqueConstraint(name = "uq_delivery_orders_id", columnNames = "orders_id")
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseAuditEntity {

  @Id
  @GeneratedValue
  @UuidGenerator
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "orders_id", nullable = false, updatable = false)
  private UUID ordersId;

  @Embedded
  private HubRoute hubRoute;

  @Column(name = "origin_hub_name", nullable = false, updatable = false)
  private String originHubName;

  @Column(name = "destination_hub_name", nullable = false, updatable = false)
  private String destinationHubName;

  @Embedded
  private Receiver receiver;

  @Embedded
  private Address address;

  @Embedded
  private Location location;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private DeliveryStatus status;

  @Column(name = "delivery_manager_id")
  private UUID deliveryManagerId;

  @Column(name = "delivery_manager_name")
  private String DeliveryManagerName;

  @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DeliveryRoute> routes = new ArrayList<>();

  private void changeStatus(DeliveryStatus next) {
    if (!this.status.canTransitionTo(next)) {

      if (next == DeliveryStatus.CANCELLED) {
        throw new BusinessException(DeliveryErrorCode.CANNOT_CANCEL_DELIVERY);
      }

      throw new BusinessException(DeliveryErrorCode.INVALID_DELIVERY_STATUS_TRANSITION);
    }

    this.status = next;
  }

  public boolean isAllRoutesCompleted() {
    if (this.routes == null || this.routes.isEmpty()) return false;
    return this.routes.stream()
        .allMatch(route -> route.getStatus() == DeliveryRouteStatus.COMPLETED);
  }

  public void startHubMoving() {
    changeStatus(DeliveryStatus.HUB_MOVING);
  }

  public void arriveDestination() {
    if (!isAllRoutesCompleted()) {
      throw new BusinessException(DeliveryErrorCode.NOT_ALL_ROUTES_COMPLETED);
    }
    changeStatus(DeliveryStatus.DESTINATION_ARRIVED);
  }

  public void startVendorMoving() {
    changeStatus(DeliveryStatus.FOR_VENDOR_MOVING);
  }

  public void complete() {
    changeStatus(DeliveryStatus.COMPLETED);
  }

  public void cancel() {
    changeStatus(DeliveryStatus.CANCELLED);
    this.routes.forEach(DeliveryRoute::cancel);
  }

  public boolean isRelatedToHub(UUID hubId) {
    return hubRoute.isRelatedTo(hubId);
  }

  public boolean isSameHub() {
    return hubRoute.isSameHub();
  }

  public boolean isCancelled() {
    return this.status == DeliveryStatus.CANCELLED;
  }

  public void updateDeliveryManagerInfo(UUID deliveryManagerId, String DeliveryManagerName) {
    this.deliveryManagerId = deliveryManagerId;
    this.DeliveryManagerName = DeliveryManagerName;
  }

  private Delivery(
      UUID ordersId, HubRoute hubRoute, String originHubName, String destinationHubName,
      Receiver receiver, Address address, Location location) {
    this.ordersId = ordersId;
    this.hubRoute = hubRoute;
    this.originHubName = originHubName;
    this.destinationHubName = destinationHubName;
    this.receiver = receiver;
    this.address = address;
    this.location = location;
    this.status = DeliveryStatus.PENDING;
  }

  public static Delivery create(
      UUID ordersId, HubRoute hubRoute, String originHubName, String destinationHubName,
      Receiver receiver, Address address, Location location) {
    return new Delivery(ordersId, hubRoute, originHubName, destinationHubName, receiver, address, location);
  }
}