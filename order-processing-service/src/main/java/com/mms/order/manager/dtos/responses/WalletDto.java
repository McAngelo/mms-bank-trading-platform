package com.mms.order.manager.dtos.responses;

import java.math.BigDecimal;

public record WalletDto (
   BigDecimal balance
) { }
