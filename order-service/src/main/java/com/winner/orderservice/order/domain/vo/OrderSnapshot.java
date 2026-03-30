package com.winner.orderservice.order.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSnapshot {

    @Column(name = "product_name", nullable = false, updatable = false, length = 100)
    private String productName;

    @Column(name = "delivery_address", nullable = false, updatable = false, length = 255)
    private String deliveryAddress;

    @Column(name = "delivery_address_detail", nullable = false, updatable = false, length = 100)
    private String deliveryAddressDetail;

    public OrderSnapshot(String productName, String deliveryAddress, String deliveryAddressDetail) {
        validateNotEmpty(productName, "상품명");
        validateNotEmpty(deliveryAddress, "배송지 주소");
        validateNotEmpty(deliveryAddressDetail, "배송지 상세 주소");
        this.productName = productName;
        this.deliveryAddress = deliveryAddress;
        this.deliveryAddressDetail = deliveryAddressDetail;
    }

    private void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + "는 비워둘 수 없습니다.");
        }
    }
}