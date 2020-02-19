package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.MayorEventsSession;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MayorEventsSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MayorEventsSessionRepository extends JpaRepository<MayorEventsSession, Long> {

}
