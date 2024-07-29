package com.mms.reporting.service.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document
public class AuditTrail {

    @Id
    Long id;
    User user;
    String action;
    String naration;
    String actionDateTime;
}
