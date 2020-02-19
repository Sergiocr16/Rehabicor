package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.IncomeDiagnosis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IncomeDiagnosis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeDiagnosisRepository extends JpaRepository<IncomeDiagnosis, Long> {

}
