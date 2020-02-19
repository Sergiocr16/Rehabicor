package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.MayorEvent;
import com.aditum.rehabicor.repository.MayorEventRepository;
import com.aditum.rehabicor.service.dto.MayorEventDTO;
import com.aditum.rehabicor.service.mapper.MayorEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MayorEvent.
 */
@Service
@Transactional
public class MayorEventService {

    private final Logger log = LoggerFactory.getLogger(MayorEventService.class);

    private final MayorEventRepository mayorEventRepository;

    private final MayorEventMapper mayorEventMapper;

    public MayorEventService(MayorEventRepository mayorEventRepository, MayorEventMapper mayorEventMapper) {
        this.mayorEventRepository = mayorEventRepository;
        this.mayorEventMapper = mayorEventMapper;
    }

    /**
     * Save a mayorEvent.
     *
     * @param mayorEventDTO the entity to save
     * @return the persisted entity
     */
    public MayorEventDTO save(MayorEventDTO mayorEventDTO) {
        log.debug("Request to save MayorEvent : {}", mayorEventDTO);
        MayorEvent mayorEvent = mayorEventMapper.toEntity(mayorEventDTO);
        mayorEvent = mayorEventRepository.save(mayorEvent);
        return mayorEventMapper.toDto(mayorEvent);
    }

    /**
     * Get all the mayorEvents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MayorEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MayorEvents");
        return mayorEventRepository.findAll(pageable)
            .map(mayorEventMapper::toDto);
    }


    /**
     * Get one mayorEvent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MayorEventDTO> findOne(Long id) {
        log.debug("Request to get MayorEvent : {}", id);
        return mayorEventRepository.findById(id)
            .map(mayorEventMapper::toDto);
    }

    /**
     * Delete the mayorEvent by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MayorEvent : {}", id);
        mayorEventRepository.deleteById(id);
    }
}
