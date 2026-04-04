package com.winner.orderservice.order.infrastructure.repository;

import com.winner.orderservice.common.PageableUtils;
import com.winner.orderservice.order.application.dto.command.SearchOrderCommand;
import com.winner.orderservice.order.domain.entity.Order;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

  public static Specification<Order> of(SearchOrderCommand cond) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      predicates.add(cb.isNull(root.get("deletedAt")));

      if (cond.status() != null) {
        predicates.add(cb.equal(root.get("status"), cond.status()));
      }
      if (cond.from() != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("orderedAt"), cond.from()));
      }
      if (cond.to() != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get("orderedAt"), cond.to()));
      }

      if (cond.hubId() != null) {
        predicates.add(cb.equal(root.get("hubId"), cond.hubId()));
      }
      if (cond.companyId() != null) {
        Predicate supplier = cb.equal(root.get("participants").get("supplierId"), cond.companyId());
        Predicate receiver = cb.equal(root.get("participants").get("receiverId"), cond.companyId());
        predicates.add(cb.or(supplier, receiver));
      }
      if (cond.deliveryId() != null) {
        predicates.add(cb.equal(root.get("deliveryId"), cond.deliveryId()));
      }
      if (cond.assignedDeliveryPersonId() != null) {
        predicates.add(cb.equal(root.get("assignedDeliveryPersonId"), cond.assignedDeliveryPersonId()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }

  public static Pageable toPageable(Pageable original, SearchOrderCommand cond) {
    int normalizedSize = PageableUtils.normalizeSize(original.getPageSize());
    String sortField = cond.resolvedSortBy();
    Sort sort = cond.isAscending()
        ? Sort.by(sortField).ascending()
        : Sort.by(sortField).descending();
    return PageRequest.of(original.getPageNumber(), normalizedSize, sort);
  }

  private OrderSpecification() {}
}
