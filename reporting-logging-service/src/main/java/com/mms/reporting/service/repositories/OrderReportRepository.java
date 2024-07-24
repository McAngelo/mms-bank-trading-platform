package com.mms.reporting.service.repositories;

import com.mms.reporting.service.models.OrderReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderReportRepository extends MongoRepository<OrderReport, String>, OrderReportRepositoryCustom {
    OrderReport findByOrderId(long orderId);

    @Query("{ 'date': { $gte: ?0, $lte: ?1 } }")
    Page<OrderReport> findByDateBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

}
