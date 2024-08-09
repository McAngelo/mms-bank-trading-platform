package com.mms.user.service.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletRequestDTO {
    private BigDecimal balance;
    private int userId;
}
