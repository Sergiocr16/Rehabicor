package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.RehabilitationGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RehabilitationGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RehabilitationGroupRepository extends JpaRepository<RehabilitationGroup, Long> {

    @Query(value = "select distinct rehabilitation_group from RehabilitationGroup rehabilitation_group left join fetch rehabilitation_group.patients",
        countQuery = "select count(distinct rehabilitation_group) from RehabilitationGroup rehabilitation_group")
    Page<RehabilitationGroup> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct rehabilitation_group from RehabilitationGroup rehabilitation_group left join fetch rehabilitation_group.patients")
    List<RehabilitationGroup> findAllWithEagerRelationships();

    @Query("select rehabilitation_group from RehabilitationGroup rehabilitation_group left join fetch rehabilitation_group.patients where rehabilitation_group.id =:id")
    Optional<RehabilitationGroup> findOneWithEagerRelationships(@Param("id") Long id);

}
