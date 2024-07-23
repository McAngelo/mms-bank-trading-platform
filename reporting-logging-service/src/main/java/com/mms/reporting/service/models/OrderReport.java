package com.mms.reporting.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class OrderReport {

    @Id
    private long orderId;
    private User user;
    private Order order;
    private List<OrderActivity> orderActivities;
    private List<Execution> executions;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public void init() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }
}
