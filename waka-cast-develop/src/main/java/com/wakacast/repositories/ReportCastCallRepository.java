package com.wakacast.repositories;

import com.wakacast.models.CastCall;
import com.wakacast.models.ReportedCastCall;
import com.wakacast.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportCastCallRepository extends JpaRepository<ReportedCastCall, Long> {
    Optional<ReportedCastCall> findByCastCallReportedAndCastCallReporter(CastCall castCallReported, User reporter);
    ReportedCastCall findReportedCastCallById(Long reportedCastCallId);
    Page<ReportedCastCall> findReportedCastCallsByCastCallReportedId(Long reportedCastCallId, Pageable pageable);
}
