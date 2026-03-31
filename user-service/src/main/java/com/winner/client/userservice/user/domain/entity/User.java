package com.winner.client.userservice.user.domain.entity;

import com.winner.client.global.entity.BaseAuditEntity;
import com.winner.client.userservice.user.domain.enums.ApprovalStatusType;
import com.winner.client.userservice.user.domain.enums.UserStatusType;
import com.winner.client.userservice.user.domain.vo.Password;
import com.winner.client.userservice.user.domain.vo.PhoneNumber;
import com.winner.client.userservice.user.domain.vo.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  private Password passwordHash;

  private PhoneNumber phoneNumber;

  @Column(length = 50)
  private String slackId;

  private UserStatusType userStatus;

  private ApprovalStatusType approvalStatus;

  private UserRole userRole;

  public User(String username, String name, Password passwordHash, PhoneNumber phoneNumber) {
    this.name = name;
    this.passwordHash = passwordHash;
    this.approvalStatus = ApprovalStatusType.PENDING;
    this.userStatus = UserStatusType.INACTIVE;
  }

  public User encodePassword(final PasswordEncoder encoder) {
    this.passwordHash = Password.encode(passwordHash.getValue(), encoder);
    return this;
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