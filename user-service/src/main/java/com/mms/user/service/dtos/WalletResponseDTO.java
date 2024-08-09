package com.mms.user.service.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletResponseDTO {
    private int id;
    private long balance;
    private UserResponseDto user;
}
