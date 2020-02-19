package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.Comorbiditie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Comorbiditie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComorbiditieRepository extends JpaRepository<Comorbiditie, Long> {

}
