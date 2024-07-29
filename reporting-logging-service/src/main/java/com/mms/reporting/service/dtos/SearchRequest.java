package com.mms.reporting.service.dtos;

import com.mms.reporting.service.enums.SearchFieldDataType;

public record SearchRequest(String field, Object value, SearchFieldDataType type) {
}
