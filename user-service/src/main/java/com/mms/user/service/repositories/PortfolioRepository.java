package com.mms.user.service.repositories;

import com.mms.user.service.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer>, JpaSpecificationExecutor<Portfolio> {
    List<Portfolio> findAllByOwnerId(Integer userId);
}
