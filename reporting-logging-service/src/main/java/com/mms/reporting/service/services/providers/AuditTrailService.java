package com.mms.reporting.service.services.providers;


import com.mms.reporting.service.dtos.auditrail.AuditResponseDto;
import com.mms.reporting.service.dtos.auditrail.CreateAuditDto;
import com.mms.reporting.service.helper.BaseFilter;
import com.mms.reporting.service.helper.IApiResponse;
import com.mms.reporting.service.helper.PagedList;
import com.mms.reporting.service.models.AuditTrail;
import com.mms.reporting.service.repositories.AuditTrailRepository;
import com.mms.reporting.service.services.interfaces.IAuditTrailService;
import com.mms.reporting.service.utils.ApiResponseUtil;
import com.mms.reporting.service.utils.DtosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditTrailService implements IAuditTrailService {

    private final AuditTrailRepository auditTrailRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditTrailService.class);

    @Autowired
    public AuditTrailService(AuditTrailRepository auditTrailRepository) {
        this.auditTrailRepository = auditTrailRepository;
    }


    @Override
    public IApiResponse<AuditResponseDto> createAuditTrail(CreateAuditDto request) {
        LOGGER.info("Creating audit trail for action: {}", request);
        AuditTrail audit  = DtosUtil.createAuditTDtoToAuditTrail(request);
        audit = auditTrailRepository.save(audit);
        LOGGER.info("Audit trail created successfully for action: {}", audit);
        var response = DtosUtil.auditTrailToAuditResponseDto(audit);

        return ApiResponseUtil.toCreatedApiResponse(response);
    }

    @Override
    public IApiResponse<AuditResponseDto> getAuditTrailById(Long id) {
        LOGGER.info("Getting audit trail by id: {}", id);
        var audit = auditTrailRepository.findById(id);
        if (audit.isEmpty()) {
            LOGGER.error("Audit trail not found for id: {}", id);
            return ApiResponseUtil.toNotFoundApiResponse();
        }
        LOGGER.info("Audit trail found for id: {}, {}", id, audit.get());
        var response = DtosUtil.auditTrailToAuditResponseDto(audit.get());
        return ApiResponseUtil.toOkApiResponse(response);
    }

    @Override
    public IApiResponse<PagedList<AuditResponseDto>> getAuditTrails(BaseFilter filter) {
        LOGGER.info("Getting audit trails with filter: {}", filter);
        var result = auditTrailRepository.findAllPaged(filter);
        LOGGER.info("Audit trails found with filter: {}, result {}", filter, result);

        int totalCount = result.keySet().stream().findFirst().orElse(0);

        List<AuditResponseDto> audits = result.get(totalCount).stream().map(DtosUtil::auditTrailToAuditResponseDto).toList();

        var response = new PagedList<>(audits, filter.page(), filter.pageSize(), totalCount);
        return ApiResponseUtil.toOkApiResponse(response);
    }

}
