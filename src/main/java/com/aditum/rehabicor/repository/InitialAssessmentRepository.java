package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.InitialAssessment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the InitialAssessment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InitialAssessmentRepository extends JpaRepository<InitialAssessment, Long> {
    Optional<InitialAssessment> findByPatientId(Long patientId);

}
