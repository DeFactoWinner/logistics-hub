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
public class OrderDetail {

    @Column(name = "product_id", nullable = false, updatable = false)
    private UUID productId;

    @Column(name = "count", nullable = false)
    private Long count;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    public OrderDetail(UUID productId, Long count, String comment) {
        validateNotNull(productId, "상품 ID");
        validateCount(count);
        this.productId = productId;
        this.count = count;
        this.comment = comment;
    }

    public OrderDetail withCount(Long newCount) {
        validateCount(newCount);
        return new OrderDetail(this.productId, newCount, this.comment);
    }

    public OrderDetail withComment(String newComment) {
        return new OrderDetail(this.productId, this.count, newComment);
    }

    private void validateNotNull(UUID value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + "는 필수입니다.");
        }
    }

    private void validateCount(Long count) {
        if (count == null) {
            throw new IllegalArgumentException("수량은 필수입니다.");
        }
        if (count < 1) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
    }
}