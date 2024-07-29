package com.mms.reporting.service.dtos.auditrail;

import com.mms.reporting.service.dtos.orderreport.UserDto;

import java.time.LocalDateTime;

public record AuditResponseDto(Long id, UserDto user, String action, String narration, LocalDateTime actionDateTime) {
}
