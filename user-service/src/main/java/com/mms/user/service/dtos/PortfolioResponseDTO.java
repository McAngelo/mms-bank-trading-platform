package com.mms.user.service.dtos;

import com.mms.user.service.model.Portfolio;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioResponseDTO {
    private int id;
    private UserResponseDto user;
    private Portfolio.PortfolioType portfolioType;
    private Portfolio.Status status;
}

