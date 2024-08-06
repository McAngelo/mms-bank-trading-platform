package com.mms.reporting.service.repositories;

import com.mms.reporting.service.helper.BaseFilter;
import com.mms.reporting.service.models.AuditTrail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;

public class AuditTrailRepositoryImpl implements AuditTrailRepositoryCustom {
    private static final Logger logger = LoggerFactory.getLogger(AuditTrailRepositoryImpl.class);

    private final MongoTemplate mongoTemplate;

    public AuditTrailRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Map<Integer, List<AuditTrail>> findAllPaged(BaseFilter filter) {
        logger.info("Executing query with filter: {}", filter);

        // Query logic goes here

        Query query = new Query();
        query.fields().include("id")
                .include("user")
                .include("action")
                .include("narration")
                .include("actionDateTime"); // Projection to include specific fields


        if(filter.fromDate() != null && filter.toDate() != null) {
            query.addCriteria(Criteria.where("createdAt").gte(filter.fromDate()).lte(filter.toDate()));
        } else if(filter.fromDate() != null) {
            query.addCriteria(Criteria.where("createdAt").gte(filter.fromDate()));
        } else if(filter.toDate() != null) {
            query.addCriteria(Criteria.where("createdAt").lte(filter.toDate()));
        }

        // sort by actionDateTime
        if (filter.sortOrder().equalsIgnoreCase("desc")) {
            query.with(Sort.by(Sort.Order.desc("actionDateTime")));
        } else {
            query.with(Sort.by(Sort.Order.asc("actionDateTime")));
        }

        var totalCount = (int)mongoTemplate.count(query, AuditTrail.class);

        // paginate
        query.skip(filter.getOffset());
        query.limit(filter.getLimit());

        return Map.of(totalCount, mongoTemplate.find(query, AuditTrail.class));
    }
}
