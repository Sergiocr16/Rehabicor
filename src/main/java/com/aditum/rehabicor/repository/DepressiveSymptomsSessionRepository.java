package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.DepressiveSymptomsSession;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DepressiveSymptomsSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepressiveSymptomsSessionRepository extends JpaRepository<DepressiveSymptomsSession, Long> {

}
