package com.mms.reporting.service.repositories;

import com.mms.reporting.service.models.OrderReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class OrderReportRepositoryImpl implements OrderReportRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(OrderReportRepositoryImpl.class);

    private final MongoTemplate mongoTemplate;

    public OrderReportRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<OrderReport> findByFields(String field, String value) {
        Criteria criteria = Criteria.where(field).is(value);
        Query query = Query.query(criteria);
        query.fields().include("orderId").include("status"); // Projection to include specific fields
        query.skip(0); // Consider parameterizing these values
        query.limit(10);

        // Debugging: Log the query to be executed
        logger.debug("Executing query: {}", query.toString());
        System.out.println("Executing query: " + query.toString());

        List<OrderReport> results = mongoTemplate.find(query, OrderReport.class);

        // Debugging: Log the number of results found
        logger.debug("Found {} results", results.size());
        System.out.println("Found " + results.size() + " results");

        return results;
    }
}
