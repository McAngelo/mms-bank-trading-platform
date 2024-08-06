package com.mms.user.service.dtos;

import com.mms.user.service.model.Portfolio;
import com.mms.user.service.model.Role;
import com.mms.user.service.model.Wallet;

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
    public List<Portfolio> portfolios;
    public List<Wallet> wallet;
    public LocalDateTime createdDate;
    public LocalDateTime lastModifiedDate;
}
