package com.mms.market_data_service.config;

import lombok.Getter;
import lombok.Setter;

// Stock Modal Class
@Setter
@Getter
public class Stock {
    String name;
    String icon;
    float price;
    boolean increased;

    public Stock(String name, String icon, float price) {
        this.name = name;
        this.icon = icon;
        this.price = price;
    }

    public void setIncreased(boolean b) {
    }
}
