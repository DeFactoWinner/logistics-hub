package com.winner.client.userservice.user.infrastructure.repository.impl;

import static com.winner.client.userservice.user.domain.entity.QUser.user;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.winner.client.global.pagination.PageSortType;
import com.winner.client.userservice.user.application.dto.query.AdminUserPageQuery;
import com.winner.client.userservice.user.application.dto.query.ManagerUserPageQuery;
import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.enums.ApprovalStatusType;
import com.winner.client.userservice.user.domain.enums.RoleType;
import com.winner.client.userservice.user.domain.enums.UserStatusType;
import com.winner.client.userservice.user.infrastructure.repository.UserQueryRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserQueryRepositoryImpl implements UserQueryRepository {

  private final JPAQueryFactory queryFactory;

  public Page<User> findAllByManagerScope(ManagerUserPageQuery query) {
    return fetchUserPage(
        query.page(),
        query.size(),
        query.sortType(),
        referenceIdEq(query.referenceId()),
        roleEq(query.role()),
        user.userStatus.eq(UserStatusType.ACTIVE),
        user.approvalStatus.eq(ApprovalStatusType.APPROVED), // 승인된 유저만
        user.deletedAt.isNull()
    );
  }

  public Page<User> findAllByAdminCondition(AdminUserPageQuery query) {
    return fetchUserPage(
        query.page(),
        query.size(),
        query.sortType(),
        referenceIdEq(query.referenceId()),
        roleEq(query.role()),
        userStatusEq(query.userStatus()),
        approvalStatusEq(query.approvalStatus()),
        user.deletedAt.isNull()
    );
  }

  private BooleanExpression referenceIdEq(UUID referenceId) {
    return referenceId != null ? user.userRole.referenceId.eq(referenceId) : null;
  }

  private BooleanExpression roleEq(RoleType role) {
    return role != null ? user.userRole.role.eq(role) : null;
  }

  private BooleanExpression userStatusEq(UserStatusType status) {
    return status != null ? user.userStatus.eq(status) : null;
  }

  private BooleanExpression approvalStatusEq(ApprovalStatusType status) {
    return status != null ? user.approvalStatus.eq(status) : null;
  }


  private Page<User> fetchUserPage(int page, int size, PageSortType sortType,
      BooleanExpression... predicates) {
    List<User> content = queryFactory
        .selectFrom(user)
        .where(predicates)
        .orderBy(orderSpecifier(sortType))
        .offset((long) page * size)
        .limit(size)
        .fetch();

    Long total = queryFactory
        .select(user.count())
        .from(user)
        .where(predicates)
        .fetchOne();

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortType.getProperty()));
    return new PageImpl<>(content, pageable, total != null ? total : 0L);
  }

  private OrderSpecifier<?> orderSpecifier(PageSortType sortType) {
    if (sortType == null) {
      return user.createdAt.desc();
    }

    return switch (sortType) {
      case CREATED_AT_ASC -> user.createdAt.asc();
      case CREATED_AT_DESC -> user.createdAt.desc();
      case UPDATED_AT_ASC -> user.updatedAt.asc();
      case UPDATED_AT_DESC -> user.updatedAt.desc();
    };
  }
}
