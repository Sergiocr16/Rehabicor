package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.FinalAssessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FinalAssessment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinalAssessmentRepository extends JpaRepository<FinalAssessment, Long> {
    Page<FinalAssessment> findAllByPatientId(Pageable page, Long patientId);

}
