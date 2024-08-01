package com.mms.order.manager.repositories;

import com.mms.order.manager.models.SplitOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SplitOrderRepositories extends JpaRepository<SplitOrder, Long> {
}
