package com.decagon.lendingservice.repo;

import com.decagon.lendingservice.entity.InvestmentPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentPreferencePaginationRepository extends JpaRepository<InvestmentPreference, Long> {
}
