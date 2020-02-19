package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.IncomeDiagnosis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the IncomeDiagnosis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeDiagnosisRepository extends JpaRepository<IncomeDiagnosis, Long> {
    Page<IncomeDiagnosis> findByRehabilitationCenterIdAndAndDeleted(Pageable pageable, Long rehabilitationId, boolean deleted);

    List<IncomeDiagnosis> findByRehabilitationCenterIdAndAndDeleted(Long rehabilitationId, boolean deleted);
}
