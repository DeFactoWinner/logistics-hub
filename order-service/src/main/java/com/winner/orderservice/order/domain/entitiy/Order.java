package com.winner.orderservice.order.domain.entitiy;

import com.winner.orderservice.order.domain.enums.OrderStatus;
import com.winner.orderservice.order.domain.vo.OrderDetail;
import com.winner.orderservice.order.domain.vo.OrderParticipants;
import com.winner.orderservice.order.domain.vo.OrderSnapshot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Embedded
    private OrderParticipants participants;

    @Embedded
    private OrderSnapshot snapshot;

    @Embedded
    private OrderDetail orderDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "delivery_id")
    private UUID deliveryId; // nullable - 배송 생성 후 주입

    @Column(name = "ordered_at", nullable = false, updatable = false)
    private LocalDateTime orderedAt;



    public static Order create(
            OrderParticipants participants,
            OrderSnapshot snapshot,
            OrderDetail orderDetail,
            UUID createdBy
    ) {
        Order order = new Order();
        order.participants = participants;
        order.snapshot = snapshot;
        order.orderDetail = orderDetail;
        order.status = OrderStatus.PENDING;
        order.orderedAt = LocalDateTime.now();
        return order;
    }

    public void update(OrderDetail newDetail, UUID updatedBy) {
        if (!status.isEditable()) {
            throw new IllegalStateException(
                    status + " 상태에서는 수정할 수 없습니다."
            );
        }
        this.orderDetail = newDetail;
    }

    public void confirm(UUID updatedBy) {
        changeStatus(OrderStatus.CONFIRMED, updatedBy);
    }

    public void cancel(UUID updatedBy) {
        if (!status.isCancellable()) {
            throw new IllegalStateException(
                    status + " 상태에서는 취소할 수 없습니다."
            );
        }
        changeStatus(OrderStatus.CANCELLED, updatedBy);
    }

    public void delete(UUID deletedBy) {
        if (!status.isDeletable()) {
            throw new IllegalStateException(
                    status + " 상태에서는 삭제할 수 없습니다."
            );
        }
    }

    public void assignDelivery(UUID deliveryId, UUID updatedBy) {
        if (deliveryId == null) {
            throw new IllegalArgumentException("배송 ID는 필수입니다.");
        }
        if (this.deliveryId != null) {
            throw new IllegalStateException("이미 배송이 배정된 주문입니다.");
        }
        this.deliveryId = deliveryId;
    }


    private void changeStatus(OrderStatus next, UUID updatedBy) {
        if (!status.canTransitionTo(next)) {
            throw new IllegalStateException(
                    String.format("%s → %s 전이는 불가합니다.", status, next)
            );
        }
        this.status = next;
    }
}