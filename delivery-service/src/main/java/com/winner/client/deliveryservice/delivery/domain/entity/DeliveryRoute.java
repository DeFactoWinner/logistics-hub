package com.winner.client.deliveryservice.delivery.domain.entity;

import com.winner.client.deliveryservice.common.exception.delivery.DeliveryErrorCode;
import com.winner.client.deliveryservice.delivery.application.dto.command.CreateDeliveryRouteCommand;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryRouteStatus;
import com.winner.client.deliveryservice.delivery.domain.vo.CurrentHubRoute;
import com.winner.client.deliveryservice.delivery.domain.vo.Distance;
import com.winner.client.deliveryservice.delivery.domain.vo.Duration;
import com.winner.client.global.exception.BusinessException;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(
    name = "p_delivery_route_steps",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_route_steps_delivery_seq",
        columnNames = {"delivery_id", "seq"}
    )
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryRoute {

  @Id
  @GeneratedValue
  @UuidGenerator
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_id", nullable = false, updatable = false)
  private Delivery delivery;

  @Column(name = "seq", nullable = false)
  private int seq;

  @Embedded
  private CurrentHubRoute currentHubRoute;

  @Column(name = "cur_hub_name", nullable = false, updatable = false)
  private String curHubName;

  @Column(name = "next_hub_name", nullable = false, updatable = false)
  private String nextHubName;

  @Embedded
  @AttributeOverride(name = "kilometers", column = @Column(name = "estimated_distance", precision = 10, scale = 2, nullable = false))
  private Distance estimatedDistance;

  @Embedded
  @AttributeOverride(name = "minutes", column = @Column(name = "estimated_arrival_time", nullable = false))
  private Duration estimatedArrivalTime;

  @Embedded
  @AttributeOverride(name = "kilometers", column = @Column(name = "actual_distance", precision = 10, scale = 2))
  private Distance actualDistance;

  @Embedded
  @AttributeOverride(name = "minutes", column = @Column(name = "actual_arrival_time"))
  private Duration actualArrivalTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private DeliveryRouteStatus status;

  @Column(name = "delivery_manager_id", nullable = false)
  private UUID deliveryManagerId;

  @Column(name = "delivery_manager_name")
  private String DeliveryManagerName;


  private static void validateSeq(int seq) {
    if (seq < 0) {
      throw new IllegalArgumentException("순번은 0 이상이어야 합니다.");
    }
  }
  private void changeStatus(DeliveryRouteStatus next) {
    if (!this.status.canTransitionTo(next)) {

      if (next == DeliveryRouteStatus.CANCELLED) {
        throw new BusinessException(DeliveryErrorCode.CANNOT_CANCEL_ROUTE);
      }

      throw new BusinessException(DeliveryErrorCode.INVALID_DELIVERY_STATUS_TRANSITION);
    }

    this.status = next;
  }

  public void assign() {
    changeStatus(DeliveryRouteStatus.ASSIGNED);
  }

  public void startProgress() {
    changeStatus(DeliveryRouteStatus.IN_PROGRESS);
  }

  public void complete() {
    changeStatus(DeliveryRouteStatus.COMPLETED);
  }

  public void cancel() {
    changeStatus(DeliveryRouteStatus.CANCELLED);
  }

  public boolean isRelatedToHub(UUID hubId) {
    return currentHubRoute.isRelatedTo(hubId); // 위임
  }

  public void updateActualDeliveryRouteInfo(BigDecimal actualDistance, int actualArrivalTime) {
    this.actualDistance = new Distance(actualDistance);
    this.actualArrivalTime = new Duration(actualArrivalTime);
  }

  public void updateDeliveryManagerInfo(UUID deliveryManagerId, String DeliveryManagerName) {
    this.deliveryManagerId = deliveryManagerId;
    this.DeliveryManagerName = DeliveryManagerName;
  }

  public DeliveryRoute(Delivery delivery, int seq, CurrentHubRoute currentHubRoute,
      String curHubName, String nextHubName, Distance estimatedDistance, Duration estimatedArrivalTime) {
    this.delivery = delivery;
    this.seq = seq;
    this.currentHubRoute = currentHubRoute;
    this.curHubName = curHubName;
    this.nextHubName = nextHubName;
    this.estimatedDistance = estimatedDistance;
    this.estimatedArrivalTime = estimatedArrivalTime;
    this.status = DeliveryRouteStatus.WAITING;
  }

  public static DeliveryRoute create(CreateDeliveryRouteCommand command) {
    return new DeliveryRoute(
        command.delivery(),
        command.sequence(),
        command.currentHubRoute(),
        command.curHubName(),
        command.nextHubName(),
        command.estimatedDistance(),
        command.estimatedArrivalTime()
    );
  }
}