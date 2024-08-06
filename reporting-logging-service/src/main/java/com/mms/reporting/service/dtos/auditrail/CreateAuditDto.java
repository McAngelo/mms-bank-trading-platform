package com.mms.reporting.service.dtos.auditrail;

import com.mms.reporting.service.dtos.orderreport.UserDto;

public record CreateAuditDto(UserDto user, String action) {
}
