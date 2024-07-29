package com.mms.reporting.service.dtos.orderreport;

import java.time.LocalDateTime;

public record OrderActivityDto(String status, LocalDateTime timestamp) {
}
