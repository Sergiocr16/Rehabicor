package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.NonSpecificPainsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NonSpecificPainsSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NonSpecificPainsSessionRepository extends JpaRepository<NonSpecificPainsSession, Long> {
    Page<NonSpecificPainsSession> findAllBySessionId(Pageable page, Long sessionId);

}
