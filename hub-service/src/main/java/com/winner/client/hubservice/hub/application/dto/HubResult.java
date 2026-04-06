package com.winner.client.hubservice.hub.application.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubResult implements Serializable {

    private UUID id;
    private String name;
    private String address;
    private double lat;
    private double lng;
}
