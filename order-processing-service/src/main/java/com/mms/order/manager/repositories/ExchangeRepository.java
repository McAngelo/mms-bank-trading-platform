package com.mms.order.manager.repositories;

import com.mms.order.manager.models.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    Optional<Exchange> findBySlug(String slug);

    Optional<String> findSlugById(Long id);

    @Query("SELECT slug FROM Exchange")
    List<String> findAllSlugs();
}