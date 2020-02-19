package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.IncomeDiagnosisPatient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IncomeDiagnosisPatient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeDiagnosisPatientRepository extends JpaRepository<IncomeDiagnosisPatient, Long> {

}
