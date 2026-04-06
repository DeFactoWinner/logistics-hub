package com.winner.client.deliveryservice.delivery.infrastructure.repository.custom;

import static com.winner.client.deliveryservice.delivery.domain.entity.QDelivery.delivery;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.winner.client.deliveryservice.delivery.application.dto.query.SearchDeliveryQuery;
import com.winner.client.deliveryservice.delivery.domain.entity.Delivery;
import com.winner.client.deliveryservice.delivery.domain.enums.DeliveryStatus;
import com.winner.client.global.pagination.PageSortType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryCustomRepositoryImpl implements DeliveryCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Delivery> getAllDeliveries(SearchDeliveryQuery query) {
    List<Delivery> content = queryFactory
        .selectFrom(delivery)
        .where(
            roleCondition(query),
            keywordCondition(query.keyword()),
            statusCondition(query.status()),
            delivery.deletedAt.isNull()
        )
        .orderBy(orderSpecifier(query.sortType()))
        .offset((long) query.page() * query.size())
        .limit(query.size())
        .fetch();

    Long total = queryFactory
        .select(delivery.count())
        .from(delivery)
        .where(
            roleCondition(query),
            keywordCondition(query.keyword()),
            statusCondition(query.status()),
            delivery.deletedAt.isNull()
        )
        .fetchOne();

    Pageable pageable = PageRequest.of(query.page(), query.size(), Sort.by(query.sortType().getProperty()));

    return new PageImpl<>(
        content,
        pageable,
        total != null ? total : 0
    );
  }

  private BooleanExpression roleCondition(SearchDeliveryQuery query) {
    return switch (query.userRole()) {
      case "MASTER" -> null;
      case "HUB_MANAGER" ->
          delivery.hubRoute.originHubId.eq(query.referenceId())
              .or(delivery.hubRoute.destinationHubId.eq(query.referenceId()));

      case "COMPANY_MANAGER" ->
          delivery.receiver.userId.eq(query.userId());

      case "DELIVERY_MANAGER" ->
          delivery.deliveryManagerId.eq(query.userId());

      default -> throw new IllegalArgumentException("지원하지 않는 권한입니다: " + query.userRole());
    };
  }

  private BooleanExpression keywordCondition(String keyword) {
    if (keyword == null || keyword.isBlank()) return null;
    String likeKeyword = "%" + keyword + "%";
    return delivery.originHubName.like(likeKeyword)
        .or(delivery.destinationHubName.like(likeKeyword));
  }

  private BooleanExpression statusCondition(DeliveryStatus status) {
    if (status == null) return null;
    return delivery.status.eq(status);
  }

  private OrderSpecifier<?> orderSpecifier(PageSortType sortType) {
    if (sortType == null) return delivery.createdAt.desc();
    return switch (sortType) {
      case CREATED_AT_ASC  -> delivery.createdAt.asc();
      case CREATED_AT_DESC -> delivery.createdAt.desc();
      case UPDATED_AT_ASC  -> delivery.updatedAt.asc();
      case UPDATED_AT_DESC -> delivery.updatedAt.desc();
    };
  }
}
