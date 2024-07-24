package com.mms.reporting.service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserDto(@Min(value = 0, message = "Id must greater than 0") long id,
                      @Size(min = 2, message = "roleId must be more than 2 characters") @NotBlank(message = "Please provide roleId")
                      String roleId, String email, String fullName, LocalDate dob) {
}
