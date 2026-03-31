package com.winner.client.deliveryservice.delivery.domain.entity;

import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryStatus;
import com.winner.client.deliveryservice.delivery.domain.vo.Address;
import com.winner.client.deliveryservice.delivery.domain.vo.HubRoute;
import com.winner.client.deliveryservice.delivery.domain.vo.Location;
import com.winner.client.deliveryservice.delivery.domain.vo.Receiver;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
public class Delivery {

  @Id
  @GeneratedValue
  @UuidGenerator
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "orders_id", nullable = false, updatable = false)
  private UUID ordersId;

  @Embedded
  private HubRoute hubRoute;

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

  private Delivery(
      UUID ordersId, HubRoute hubRoute, Receiver receiver,
      Address address, Location location, UUID deliveryManagerId) {
    this.ordersId = ordersId;
    this.hubRoute = hubRoute;
    this.receiver = receiver;
    this.address = address;
    this.location = location;
    this.status = DeliveryStatus.CREATED;
    this.deliveryManagerId = deliveryManagerId;
  }

  public static Delivery create(
      UUID ordersId, HubRoute hubRoute, Receiver receiver,
      Address address, Location location, UUID deliveryManagerId) {
    return new Delivery(ordersId, hubRoute, receiver, address, location, deliveryManagerId);
  }
}