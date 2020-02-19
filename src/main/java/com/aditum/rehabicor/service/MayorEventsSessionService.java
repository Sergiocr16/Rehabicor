package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.MayorEventsSession;
import com.aditum.rehabicor.repository.MayorEventsSessionRepository;
import com.aditum.rehabicor.service.dto.MayorEventsSessionDTO;
import com.aditum.rehabicor.service.mapper.MayorEventsSessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MayorEventsSession.
 */
@Service
@Transactional
public class MayorEventsSessionService {

    private final Logger log = LoggerFactory.getLogger(MayorEventsSessionService.class);

    private final MayorEventsSessionRepository mayorEventsSessionRepository;

    private final MayorEventsSessionMapper mayorEventsSessionMapper;

    public MayorEventsSessionService(MayorEventsSessionRepository mayorEventsSessionRepository, MayorEventsSessionMapper mayorEventsSessionMapper) {
        this.mayorEventsSessionRepository = mayorEventsSessionRepository;
        this.mayorEventsSessionMapper = mayorEventsSessionMapper;
    }

    /**
     * Save a mayorEventsSession.
     *
     * @param mayorEventsSessionDTO the entity to save
     * @return the persisted entity
     */
    public MayorEventsSessionDTO save(MayorEventsSessionDTO mayorEventsSessionDTO) {
        log.debug("Request to save MayorEventsSession : {}", mayorEventsSessionDTO);
        MayorEventsSession mayorEventsSession = mayorEventsSessionMapper.toEntity(mayorEventsSessionDTO);
        mayorEventsSession = mayorEventsSessionRepository.save(mayorEventsSession);
        return mayorEventsSessionMapper.toDto(mayorEventsSession);
    }
    @Transactional(readOnly = true)
    public Page<MayorEventsSessionDTO> findAllBySession(Pageable pageable, Long sessionId) {
        log.debug("Request to get all MayorEventsSessions");
        return mayorEventsSessionRepository.findAllBySessionId(pageable,sessionId)
            .map(mayorEventsSessionMapper::toDto);
    }

    /**
     * Get all the mayorEventsSessions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MayorEventsSessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MayorEventsSessions");
        return mayorEventsSessionRepository.findAll(pageable)
            .map(mayorEventsSessionMapper::toDto);
    }


    /**
     * Get one mayorEventsSession by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MayorEventsSessionDTO> findOne(Long id) {
        log.debug("Request to get MayorEventsSession : {}", id);
        return mayorEventsSessionRepository.findById(id)
            .map(mayorEventsSessionMapper::toDto);
    }

    /**
     * Delete the mayorEventsSession by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MayorEventsSession : {}", id);
        mayorEventsSessionRepository.deleteById(id);
    }
}
