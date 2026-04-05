package com.winner.client.userservice.user.domain.entity;

import com.winner.client.global.entity.BaseAuditEntity;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.userservice.common.exception.UserErrorCode;
import com.winner.client.userservice.user.domain.enums.ApprovalStatusType;
import com.winner.client.userservice.user.domain.enums.UserStatusType;
import com.winner.client.userservice.user.domain.vo.Password;
import com.winner.client.userservice.user.domain.vo.PhoneNumber;
import com.winner.client.userservice.user.domain.vo.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_user")
public class User extends BaseAuditEntity {

  @Id
  @GeneratedValue
  @UuidGenerator
  @Column(name = "user_id", nullable = false)
  private UUID id;

  @Column(nullable = false, length = 15)
  private String username;

  @Column(nullable = false, length = 20)
  private String name;

  @Embedded
  private Password passwordHash;

  @Embedded
  private PhoneNumber phoneNumber;

  @Column(length = 50)
  private String slackId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserStatusType userStatus;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ApprovalStatusType approvalStatus;

  @Embedded
  private UserRole userRole;

  public static User create(
      String username, String name, Password passwordHash,
      PhoneNumber phoneNumber, String slackId, UserRole userRole
  ) {
    User user = new User();
    user.username = username;
    user.name = name;
    user.passwordHash = passwordHash;
    user.phoneNumber = phoneNumber;
    user.userRole = userRole;
    user.slackId = slackId;
    user.approvalStatus = ApprovalStatusType.PENDING;
    user.userStatus = UserStatusType.INACTIVE;
    return user;
  }

  public void assignRole(UserRole userRole) {
    this.userRole = userRole;
  }

  public boolean isApprove() {
    return approvalStatus == ApprovalStatusType.APPROVED;
  }

  public boolean isActive() {
    return userStatus == UserStatusType.ACTIVE;
  }

  public String getRoleName() {
    if (userRole == null || userRole.getRole() == null) {
      throw new BusinessException(UserErrorCode.INVALID_ROLE);
    }
    return userRole.getRole().name();
  }

  public UUID getReferenceId() {
    if (userRole == null) {
      return null;
    }
    return userRole.getReferenceId();
  }

  public boolean isCorrectPassword(String password, PasswordEncoder passwordEncoder) {
    return passwordHash.matches(password, passwordEncoder);
  }

  public void approve() {
    this.approvalStatus = ApprovalStatusType.APPROVED;
  }

  public void reject() {
    this.approvalStatus = ApprovalStatusType.REJECTED;
  }

  public void active() {
    this.userStatus = UserStatusType.ACTIVE;
  }

  public void inactive() {
    this.userStatus = UserStatusType.INACTIVE;
  }

  public void delete() {
    this.softDelete(this.id);
  }
}