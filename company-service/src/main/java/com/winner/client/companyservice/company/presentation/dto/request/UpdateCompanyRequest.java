package com.winner.client.companyservice.company.presentation.dto.request;

import java.util.UUID;

public record UpdateCompanyRequest(
    String name,
    String address,
    String addressDetail,
    UUID hubId
) {

}
