package com.mms.reporting.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class AuditTrail {

    @JsonIgnore
    public static long ID = 1L;

    @Id
    private long id  = System.currentTimeMillis();
    private User user;
    private String action;
    private String narration;
    private LocalDateTime actionDateTime;
}
