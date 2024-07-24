package com.mms.reporting.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;
    private String roleId;
    private String email;
    private String fullName;
    private LocalDate dob;
}
