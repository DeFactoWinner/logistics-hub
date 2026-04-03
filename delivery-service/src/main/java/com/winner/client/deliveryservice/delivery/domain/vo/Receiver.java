package com.winner.client.deliveryservice.delivery.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receiver {
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "receiver", nullable = false, length = 20)
  private String receiver;

  @Column(name = "slack_id", nullable = false, length = 50)
  private String slackId;

  public Receiver(UUID userId, String receiver, String slackId) {
    this.userId = userId;
    this.receiver = receiver;
    this.slackId = slackId;
  }
}
