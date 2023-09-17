package com.wakacast.repositories;

import com.wakacast.models.CastCall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CastCallRepository extends JpaRepository<CastCall, Long> {
    List<CastCall> findCastCallByPublisherId(Long userId);
    CastCall findCastCallByPublisherEmailAndId(String email, Long castCallId);

    @Query("from CastCall cc where cc.postExpiryDate >= CURRENT_DATE and cc.isReportedEnough = false")
    Page<CastCall> findCastCallsByPostExpiryDateBefore(Pageable pageable);

    List<CastCall> findCastCallsByCreateDateIsAfterAndTalentSkillContainingAndIsReportedEnough(
            LocalDateTime dateTime, String keyword, boolean flag
    );

    List<CastCall> findCastCallsByCreateDateIsAfterAndTalentSkillContainingAndStateContainingAndIsReportedEnough(
            LocalDateTime dateTime, String keyword, String location, boolean flag
    );

    CastCall findCastCallById(long castCallId);

    Page<CastCall> findCastCallsByPublisherEmail(String email, Pageable pageable);
    Page<CastCall> findAll(Pageable pageable);
}
