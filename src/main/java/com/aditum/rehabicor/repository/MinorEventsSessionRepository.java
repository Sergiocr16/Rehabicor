package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.MinorEventsSession;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MinorEventsSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MinorEventsSessionRepository extends JpaRepository<MinorEventsSession, Long> {

}
