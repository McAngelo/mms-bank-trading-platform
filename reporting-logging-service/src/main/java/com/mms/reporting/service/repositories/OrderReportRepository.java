package com.mms.reporting.service.repositories;

import com.mms.reporting.service.models.OrderReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReportRepository extends MongoRepository<OrderReport, String> {
}
