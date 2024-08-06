package com.mms.reporting.service.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode
public class OrderReport implements Comparable<OrderReport> {

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

    @Override
    public int compareTo(OrderReport o) {
        LocalDateTime createdAt1 = this.getCreatedAt();
        LocalDateTime createdAt2 = o.getCreatedAt();
        if (createdAt1 == null && createdAt2 == null) return 0;
        if (createdAt1 == null) return 1; // Consider nulls last
        if (createdAt2 == null) return -1;
        return this.getCreatedAt().compareTo(o.getCreatedAt());
    }
}
