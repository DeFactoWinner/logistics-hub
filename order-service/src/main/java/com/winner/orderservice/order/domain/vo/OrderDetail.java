package com.winner.orderservice.order.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetail {

    @Column(name = "count", nullable = false)
    private Long count;

    @Column(name = "comment", columnDefinition = "text")
    private String comment; // nullable

    public OrderDetail(Long count, String comment) {
        validateCount(count);
        this.count = count;
        this.comment = comment;
    }

    public OrderDetail withCount(Long newCount) {
        validateCount(newCount);
        return new OrderDetail(newCount, this.comment);
    }

    public OrderDetail withComment(String newComment) {
        return new OrderDetail(this.count, newComment);
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