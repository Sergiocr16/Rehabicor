package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.RehabilitationCenter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RehabilitationCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RehabilitationCenterRepository extends JpaRepository<RehabilitationCenter, Long> {

}
