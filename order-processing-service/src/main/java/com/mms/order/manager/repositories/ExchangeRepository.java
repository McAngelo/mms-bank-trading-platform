package com.mms.order.manager.repositories;

import com.mms.order.manager.models.Exchange;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepository extends Repository<Exchange, Long> {
    Optional<Exchange> findBySlug(String slug);
    Optional<String> findSlugById(Long id);
    List<String> findAllSlugs();
}