package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.Comorbiditie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Comorbiditie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComorbiditieRepository extends JpaRepository<Comorbiditie, Long> {
    Page<Comorbiditie> findByRehabilitationCenterIdAndAndDeleted(Pageable pageable, Long rehabilitationId, boolean deleted);

    List<Comorbiditie> findByRehabilitationCenterIdAndAndDeleted(Long rehabilitationId, boolean deleted);

}
