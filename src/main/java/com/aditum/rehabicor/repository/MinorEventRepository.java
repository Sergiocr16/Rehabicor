package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.MinorEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MinorEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MinorEventRepository extends JpaRepository<MinorEvent, Long> {

}
