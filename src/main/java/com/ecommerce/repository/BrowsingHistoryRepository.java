package com.ecommerce.repository;

import com.ecommerce.model.BrowsingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BrowsingHistoryRepository extends JpaRepository<BrowsingHistory, Long> {
    List<BrowsingHistory> findByBuyerId(Long buyerId);
}
