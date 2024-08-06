package com.mms.order.manager.dtos.cache;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookSummary {
    @JsonProperty("product")
    private String product;

    @JsonProperty("totalBuyQuantity")
    private long totalBuyQuantity;

    @JsonProperty("totalSellQuantity")
    private long totalSellQuantity;
}
