package com.mms.reporting.service.controller;


import com.mms.reporting.service.dtos.auditrail.AuditResponseDto;
import com.mms.reporting.service.dtos.auditrail.CreateAuditDto;
import com.mms.reporting.service.helper.BaseFilter;
import com.mms.reporting.service.helper.IApiResponse;
import com.mms.reporting.service.helper.PagedList;
import com.mms.reporting.service.services.AuditTrailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/audit-trail")
public class AuditTrailController {

    private final AuditTrailService auditTrailService;

    @Autowired
    public AuditTrailController(AuditTrailService auditTrailService) {
        this.auditTrailService = auditTrailService;
    }

    @Operation(summary = "Create an audit trail", description = "Create an audit trail", tags = {"Audit Trail"})
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Successfully created an audit trail", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class, type = "object", anyOf = {AuditResponseDto.class}))}),

            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json", headers = {"X-API-VERSION=1"})
    public ResponseEntity<IApiResponse<AuditResponseDto>> createAuditTrail(@Valid @RequestBody CreateAuditDto request) {

        var result = auditTrailService.createAuditTrail(request);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @Operation(summary = "Get an audit trail", description = "Get an audit trail by id", tags = {"Audit Trail"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved an audit trail by the id provided", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))}), @ApiResponse(responseCode = "404", description = "Couldn't find an audit trail by the id provided", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
    @GetMapping(value = "/{id}", produces = "application/json", headers = {"X-API-VERSION=1"})
    public ResponseEntity<IApiResponse<AuditResponseDto>> getAuditTrailById(@PathVariable long id) {

        var result = auditTrailService.getAuditTrailById(id);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @Operation(summary = "Search for audit trails", description = "Get paginated list of audit trails", tags = {"Audit Trail"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieves paginated list of audit trails", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
    @GetMapping(value = "all", produces = "application/json", headers = {"X-API-VERSION=1"})
    public ResponseEntity<IApiResponse<PagedList<AuditResponseDto>>> getOrderReports(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false) LocalDateTime fromDate, @RequestParam(required = false) LocalDateTime toDate, @RequestParam(required = false) String sortOrder) {
        BaseFilter filter = new BaseFilter(page, pageSize, fromDate, toDate, sortOrder);
        var result = auditTrailService.getAuditTrails(filter);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

//    @Operation(summary = "Search for audit trails", description = "Get paginated list of audit trails", tags = {"Audit Trail"})
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieves paginated list of audit trails", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
//    @GetMapping(value = "all", produces = "application/json", headers = {"X-API-VERSION=1"})
//    public ResponseEntity<IApiResponse<PagedList<AuditResponseDto>>> getOrderReports(@ParameterObject BaseFilter filter) {
//        var result = auditTrailService.getAuditTrails(filter);
//        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
//        return bd.body(result);
//    }
}
