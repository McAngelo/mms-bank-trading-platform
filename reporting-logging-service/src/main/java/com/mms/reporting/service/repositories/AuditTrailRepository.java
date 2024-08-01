package com.mms.reporting.service.repositories;

import com.mms.reporting.service.models.AuditTrail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditTrailRepository extends MongoRepository<AuditTrail, Long>, AuditTrailRepositoryCustom {
}
