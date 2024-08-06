package com.mms.order.manager.dtos.requests;

public record CreatePortfolioDto(
        long ownerId,
        String name
) { }
