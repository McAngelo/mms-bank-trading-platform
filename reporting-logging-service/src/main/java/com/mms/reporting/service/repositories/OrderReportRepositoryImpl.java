package com.mms.reporting.service.repositories;

import com.mms.reporting.service.dtos.SearchRequest;
import com.mms.reporting.service.enums.SearchFieldDataType;
import com.mms.reporting.service.models.OrderReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class OrderReportRepositoryImpl implements OrderReportRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(OrderReportRepositoryImpl.class);

    private final MongoTemplate mongoTemplate;

    public OrderReportRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<OrderReport> findByFields(String field, Object value, SearchFieldDataType type) {

        Query query = new Query();
        query.fields().include("orderId")
                .include("user")
                .include("order")
                .include("orderActivities")
                .include("executions"); // Projection to include specific fields

//        query.skip(0); // Consider parameterizing these values
//        query.limit(10);

        switch (type){
            case STRING:
                query.addCriteria(Criteria.where(field).regex(value.toString()));
                break;
            case INT:
                query.addCriteria(Criteria.where(field).is(Integer.parseInt(value.toString())));
                break;
            case LONG:
                query.addCriteria(Criteria.where(field).is(Long.parseLong(value.toString())));
                break;
            case BOOLEAN:
                query.addCriteria(Criteria.where(field).is(Boolean.parseBoolean(value.toString())));
                break;
            case DATE:
                query.addCriteria(Criteria.where(field).is(value));
                break;
            default:
                break;
        }

        // Debugging: Log the query to be executed
        logger.debug("Executing query: {}", query.toString());
        System.out.println("Executing query: " + query.toString());

        List<OrderReport> results = mongoTemplate.find(query, OrderReport.class);

        // Debugging: Log the number of results found
        logger.debug("Found {} results", results.size());
        System.out.println("Found " + results.size() + " results");

        return results;
    }

    @Override
    public Map<Integer, List<OrderReport>> findByFields(List<SearchRequest> searchRequests, int skip, int limit, LocalDateTime fromDate, LocalDateTime toDate) {
        Query query = new Query();
        query.fields().include("orderId")
                .include("user")
                .include("order")
                .include("orderActivities")
                .include("executions")
                .include("createdAt"); // Projection to include specific fields

        searchRequests.forEach(searchRequest -> {
            switch (searchRequest.type()) {
                case STRING:
                    query.addCriteria(Criteria.where(searchRequest.field()).regex(searchRequest.value().toString()));
                    break;
                case INT:
                    query.addCriteria(Criteria.where(searchRequest.field()).is(Integer.parseInt(searchRequest.value().toString())));
                    break;
                case LONG:
                    query.addCriteria(Criteria.where(searchRequest.field()).is(Long.parseLong(searchRequest.value().toString())));
                    break;
                case BOOLEAN:
                    query.addCriteria(Criteria.where(searchRequest.field()).is(Boolean.parseBoolean(searchRequest.value().toString())));
                    break;
                case DATE:
                    query.addCriteria(Criteria.where(searchRequest.field()).is(searchRequest.value()));
                    break;
                default:
                    break;
            }
        });

        if(fromDate != null && toDate != null) {
            query.addCriteria(Criteria.where("createdAt").gte(fromDate).lte(toDate));
        } else if(fromDate != null) {
            query.addCriteria(Criteria.where("createdAt").gte(fromDate));
        } else if(toDate != null) {
            query.addCriteria(Criteria.where("createdAt").lte(toDate));
        }

        var totalCount = (int)mongoTemplate.count(query, OrderReport.class);

        query.skip(skip);
        query.limit(limit);

        return Map.of(totalCount, mongoTemplate.find(query, OrderReport.class));
    }
}
