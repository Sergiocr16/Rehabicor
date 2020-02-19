package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.DepressiveSymptom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DepressiveSymptom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepressiveSymptomRepository extends JpaRepository<DepressiveSymptom, Long> {
    Page<DepressiveSymptom> findByRehabilitationCenterIdAndAndDeleted(Pageable pageable, Long rehabilitationId, boolean deleted);

}
