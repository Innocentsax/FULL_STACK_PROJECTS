package com.wakacast.repositories;

import com.wakacast.models.ReasonForReportingCastCall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReasonForReportingCastCallRepository extends JpaRepository<ReasonForReportingCastCall, Long> {
    Optional<ReasonForReportingCastCall> findReasonForReportingCastCallByContent(String content);
    List<ReasonForReportingCastCall> findAll();
}
