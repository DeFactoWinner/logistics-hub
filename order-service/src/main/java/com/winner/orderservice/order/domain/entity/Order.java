package com.winner.orderservice.order.domain.entity;

import com.winner.orderservice.order.domain.enums.OrderStatus;
import com.winner.orderservice.order.domain.vo.OrderDetail;
import com.winner.orderservice.order.domain.vo.OrderParticipants;
import com.winner.orderservice.order.domain.vo.OrderSnapshot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
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
    private UUID deliveryId;

    @Column(name = "ordered_at", nullable = false, updatable = false)
    private LocalDateTime orderedAt;

    public static Order create(
            OrderParticipants participants,
            OrderSnapshot snapshot,
            OrderDetail orderDetail
    ) {
        validateNotNull(participants, "거래 당사자");
        validateNotNull(snapshot, "주문 스냅샷");
        validateNotNull(orderDetail, "주문 상세");

        Order order = new Order();
        order.participants = participants;
        order.snapshot = snapshot;
        order.orderDetail = orderDetail;
        order.status = OrderStatus.PENDING;
        order.orderedAt = LocalDateTime.now();
        return order;
    }

    private static void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + "는 필수입니다.");
        }
    }
}