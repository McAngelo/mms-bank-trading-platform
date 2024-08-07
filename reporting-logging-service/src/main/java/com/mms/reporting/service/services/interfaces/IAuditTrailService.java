package com.mms.reporting.service.services.interfaces;

import com.mms.reporting.service.dtos.auditrail.AuditResponseDto;
import com.mms.reporting.service.dtos.auditrail.CreateAuditDto;
import com.mms.reporting.service.helper.BaseFilter;
import com.mms.reporting.service.helper.IApiResponse;
import com.mms.reporting.service.helper.PagedList;

public interface IAuditTrailService {
    IApiResponse<AuditResponseDto> createAuditTrail(CreateAuditDto request);
    IApiResponse<AuditResponseDto> getAuditTrailById(Long id);
    IApiResponse<PagedList<AuditResponseDto>> getAuditTrails(BaseFilter filter);
}
