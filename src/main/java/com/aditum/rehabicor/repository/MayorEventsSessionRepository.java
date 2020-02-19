package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.MayorEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MayorEventsSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MayorEventsSessionRepository extends JpaRepository<MayorEventsSession, Long> {
    Page<MayorEventsSession> findAllBySessionId(Pageable page, Long sessionId);

}
