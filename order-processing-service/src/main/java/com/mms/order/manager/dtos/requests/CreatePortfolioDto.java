package com.mms.order.manager.dtos.requests;

public record CreatePortfolioDto(
        long userId,
        String name
) { }
