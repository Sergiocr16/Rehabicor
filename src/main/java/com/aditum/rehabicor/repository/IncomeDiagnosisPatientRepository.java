package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.IncomeDiagnosisPatient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the IncomeDiagnosisPatient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeDiagnosisPatientRepository extends JpaRepository<IncomeDiagnosisPatient, Long> {
    List<IncomeDiagnosisPatient> findByInitialAssessmentId(Long initialAssessmentId);
    Optional<IncomeDiagnosisPatient> findFirstByInitialAssessmentIdAndIncomeDiagnosisRelation(Long initialAssessmentId, Long incomeDiagnosisId);
}
