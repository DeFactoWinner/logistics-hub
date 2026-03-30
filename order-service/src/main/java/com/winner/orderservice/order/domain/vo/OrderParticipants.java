package com.winner.orderservice.order.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderParticipants {

    @Column(name = "supplier_id", nullable = false, updatable = false)
    private UUID supplierId;

    @Column(name = "receiver_id", nullable = false, updatable = false)
    private UUID receiverId;

    @Column(name = "product_id", nullable = false, updatable = false)
    private UUID productId;

    public OrderParticipants(UUID supplierId, UUID receiverId, UUID productId) {
        validateNotNull(supplierId, "공급 업체 ID");
        validateNotNull(receiverId, "수령 업체 ID");
        validateNotNull(productId, "상품 ID");
        this.supplierId = supplierId;
        this.receiverId = receiverId;
        this.productId = productId;
    }

    private void validateNotNull(UUID value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + "는 필수입니다.");
        }
    }
}