package com.mms.user.service.dtos;

import com.mms.user.service.model.Wallet;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletRequestDTO {
    private long balance;
    private Wallet.Status status;
    private int userId;
}
