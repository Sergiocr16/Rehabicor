package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.RehabilitationGroup;
import com.aditum.rehabicor.repository.RehabilitationGroupRepository;
import com.aditum.rehabicor.service.dto.RehabilitationGroupDTO;
import com.aditum.rehabicor.service.mapper.RehabilitationGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RehabilitationGroup.
 */
@Service
@Transactional
public class RehabilitationGroupService {

    private final Logger log = LoggerFactory.getLogger(RehabilitationGroupService.class);

    private final RehabilitationGroupRepository rehabilitationGroupRepository;

    private final RehabilitationGroupMapper rehabilitationGroupMapper;

    public RehabilitationGroupService(RehabilitationGroupRepository rehabilitationGroupRepository, RehabilitationGroupMapper rehabilitationGroupMapper) {
        this.rehabilitationGroupRepository = rehabilitationGroupRepository;
        this.rehabilitationGroupMapper = rehabilitationGroupMapper;
    }

    /**
     * Save a rehabilitationGroup.
     *
     * @param rehabilitationGroupDTO the entity to save
     * @return the persisted entity
     */
    public RehabilitationGroupDTO save(RehabilitationGroupDTO rehabilitationGroupDTO) {
        log.debug("Request to save RehabilitationGroup : {}", rehabilitationGroupDTO);
        RehabilitationGroup rehabilitationGroup = rehabilitationGroupMapper.toEntity(rehabilitationGroupDTO);
        rehabilitationGroup = rehabilitationGroupRepository.save(rehabilitationGroup);
        return rehabilitationGroupMapper.toDto(rehabilitationGroup);
    }

    /**
     * Get all the rehabilitationGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RehabilitationGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RehabilitationGroups");
        return rehabilitationGroupRepository.findAll(pageable)
            .map(rehabilitationGroupMapper::toDto);
    }

    /**
     * Get all the RehabilitationGroup with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<RehabilitationGroupDTO> findAllWithEagerRelationships(Pageable pageable) {
        return rehabilitationGroupRepository.findAllWithEagerRelationships(pageable).map(rehabilitationGroupMapper::toDto);
    }
    

    /**
     * Get one rehabilitationGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RehabilitationGroupDTO> findOne(Long id) {
        log.debug("Request to get RehabilitationGroup : {}", id);
        return rehabilitationGroupRepository.findOneWithEagerRelationships(id)
            .map(rehabilitationGroupMapper::toDto);
    }

    /**
     * Delete the rehabilitationGroup by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RehabilitationGroup : {}", id);
        rehabilitationGroupRepository.deleteById(id);
    }
}
