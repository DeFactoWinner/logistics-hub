package com.winner.client.hubservice.hub.presentation.dto;

import java.io.Serializable;
import java.util.List;

public record HubPageResponse(

    List<HubResponse> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean last
) implements Serializable {}
