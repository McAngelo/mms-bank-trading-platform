package com.mms.order.manager.repositories;

import com.mms.order.manager.models.Execution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {
    List<Execution> findAllByOrderSplitId(long id);

    void deleteAllByOrderSplitId(long id);
}
