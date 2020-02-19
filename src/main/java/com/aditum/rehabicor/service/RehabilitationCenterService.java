package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.RehabilitationCenter;
import com.aditum.rehabicor.repository.RehabilitationCenterRepository;
import com.aditum.rehabicor.service.dto.RehabilitationCenterDTO;
import com.aditum.rehabicor.service.mapper.RehabilitationCenterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RehabilitationCenter.
 */
@Service
@Transactional
public class RehabilitationCenterService {

    private final Logger log = LoggerFactory.getLogger(RehabilitationCenterService.class);

    private final RehabilitationCenterRepository rehabilitationCenterRepository;

    private final RehabilitationCenterMapper rehabilitationCenterMapper;

    public RehabilitationCenterService(RehabilitationCenterRepository rehabilitationCenterRepository, RehabilitationCenterMapper rehabilitationCenterMapper) {
        this.rehabilitationCenterRepository = rehabilitationCenterRepository;
        this.rehabilitationCenterMapper = rehabilitationCenterMapper;
    }

    /**
     * Save a rehabilitationCenter.
     *
     * @param rehabilitationCenterDTO the entity to save
     * @return the persisted entity
     */
    public RehabilitationCenterDTO save(RehabilitationCenterDTO rehabilitationCenterDTO) {
        log.debug("Request to save RehabilitationCenter : {}", rehabilitationCenterDTO);
        RehabilitationCenter rehabilitationCenter = rehabilitationCenterMapper.toEntity(rehabilitationCenterDTO);
        rehabilitationCenter = rehabilitationCenterRepository.save(rehabilitationCenter);
        return rehabilitationCenterMapper.toDto(rehabilitationCenter);
    }

    /**
     * Get all the rehabilitationCenters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Transactional(readOnly = true)
    public Page<RehabilitationCenterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RehabilitationCenters");
        return rehabilitationCenterRepository.findByDeleted(pageable,false)
            .map(rehabilitationCenterMapper::toDto);
    }

    /**
     * Get one rehabilitationCenter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RehabilitationCenterDTO> findOne(Long id) {
        log.debug("Request to get RehabilitationCenter : {}", id);
        return rehabilitationCenterRepository.findById(id)
            .map(rehabilitationCenterMapper::toDto);
    }

    /**
     * Delete the rehabilitationCenter by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RehabilitationCenter : {}", id);
        rehabilitationCenterRepository.deleteById(id);
    }


}
