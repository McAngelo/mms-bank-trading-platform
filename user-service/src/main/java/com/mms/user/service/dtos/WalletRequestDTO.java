package com.mms.user.service.dtos;

import com.mms.user.service.model.Wallet;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletRequestDTO {
    private BigDecimal balance;
    private Wallet.Status status;
    private int userId;
}
