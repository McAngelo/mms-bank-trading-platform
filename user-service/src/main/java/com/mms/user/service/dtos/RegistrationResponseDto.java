package com.mms.user.service.dtos;

import com.mms.user.service.model.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RegistrationResponseDto {
    public int userId;
    public String fullName;
    public String email;
    public boolean accountLocked;
    public boolean enabled;
    public String authorities;
    public List<Role> roles;
    public LocalDateTime createdDate;
    public LocalDateTime lastModifiedDate;
}
