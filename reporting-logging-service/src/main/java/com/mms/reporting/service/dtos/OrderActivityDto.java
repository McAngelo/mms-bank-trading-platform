package com.mms.reporting.service.dtos;

import java.time.LocalDateTime;

public record OrderActivityDto(String status, LocalDateTime timestamp) {
}
