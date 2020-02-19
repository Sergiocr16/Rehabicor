package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.ComorbiditiesPatient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the ComorbiditiesPatient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComorbiditiesPatientRepository extends JpaRepository<ComorbiditiesPatient, Long> {

    Optional<ComorbiditiesPatient> findFirstByInitialAssessmentIdAndComorbiditieRelation(Long initialAssessmentId, Long comorbiditietId);
    List<ComorbiditiesPatient> findByInitialAssessmentId(Long initialAssessmentId);

}
