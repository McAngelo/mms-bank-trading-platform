package com.mms.reporting.service.dtos;

import java.time.LocalDate;

public record UserDto(long id, String roleId, String email, String fullName, LocalDate dob) {
}
