package com.winner.client.deliveryservice.deliverymanagerhub.infrastructure;

import com.winner.client.deliveryservice.deliverymanagerhub.domain.entity.DeliveryManagerHub;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDeliveryManagerHubRepository extends JpaRepository<DeliveryManagerHub, UUID> {


}
