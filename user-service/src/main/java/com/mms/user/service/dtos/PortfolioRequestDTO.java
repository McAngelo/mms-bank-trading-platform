package com.mms.user.service.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioRequestDTO {
    private long userId;
    private String name;
}
