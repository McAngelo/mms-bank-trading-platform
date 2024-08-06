package com.mms.order.manager.dtos.requests;

import java.math.BigDecimal;

public record CreateWalletDto(
    long userId,
    BigDecimal balance
) { }
