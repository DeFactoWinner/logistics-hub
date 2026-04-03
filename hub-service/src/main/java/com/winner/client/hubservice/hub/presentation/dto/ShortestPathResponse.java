package com.winner.client.hubservice.hub.presentation.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShortestPathResponse {

    private int count;
    private List<HubNodeResponse> nodes;
}
