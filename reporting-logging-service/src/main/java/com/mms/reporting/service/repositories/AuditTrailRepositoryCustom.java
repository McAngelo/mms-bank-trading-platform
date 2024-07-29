package com.mms.reporting.service.repositories;

import com.mms.reporting.service.helper.BaseFilter;
import com.mms.reporting.service.models.AuditTrail;

import java.util.List;
import java.util.Map;

public interface AuditTrailRepositoryCustom {
    Map<Integer, List<AuditTrail>> findAllPaged(BaseFilter filter);
}
