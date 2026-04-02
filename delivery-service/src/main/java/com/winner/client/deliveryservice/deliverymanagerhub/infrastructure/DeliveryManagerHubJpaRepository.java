package com.winner.client.deliveryservice.deliverymanagerhub.infrastructure;

import com.winner.client.deliveryservice.deliverymanagerhub.domain.repository.DeliveryManagerHubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryManagerHubJpaRepository implements DeliveryManagerHubRepository {

	private final SpringDataDeliveryManagerHubRepository repository;


}
