package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.MayorEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MayorEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MayorEventRepository extends JpaRepository<MayorEvent, Long> {
    Page<MayorEvent> findByRehabilitationCenterIdAndAndDeleted(Pageable pageable, Long rehabilitationId, boolean deleted);

}
