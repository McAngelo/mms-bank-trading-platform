package com.mms.user.service.dtos;

import com.mms.user.service.model.Portfolio;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioRequestDTO {
    private int userId;
    private String portfolioName;
    private Portfolio.PortfolioType portfolioType = Portfolio.PortfolioType.DEFAULT;
    private Portfolio.Status status = Portfolio.Status.ACTIVE;
}
