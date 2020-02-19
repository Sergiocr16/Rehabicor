package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.MinorEventsSession;
import com.aditum.rehabicor.repository.MinorEventsSessionRepository;
import com.aditum.rehabicor.service.dto.MinorEventsSessionDTO;
import com.aditum.rehabicor.service.mapper.MinorEventsSessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MinorEventsSession.
 */
@Service
@Transactional
public class MinorEventsSessionService {

    private final Logger log = LoggerFactory.getLogger(MinorEventsSessionService.class);

    private final MinorEventsSessionRepository minorEventsSessionRepository;

    private final MinorEventsSessionMapper minorEventsSessionMapper;

    public MinorEventsSessionService(MinorEventsSessionRepository minorEventsSessionRepository, MinorEventsSessionMapper minorEventsSessionMapper) {
        this.minorEventsSessionRepository = minorEventsSessionRepository;
        this.minorEventsSessionMapper = minorEventsSessionMapper;
    }

    /**
     * Save a minorEventsSession.
     *
     * @param minorEventsSessionDTO the entity to save
     * @return the persisted entity
     */
    public MinorEventsSessionDTO save(MinorEventsSessionDTO minorEventsSessionDTO) {
        log.debug("Request to save MinorEventsSession : {}", minorEventsSessionDTO);
        MinorEventsSession minorEventsSession = minorEventsSessionMapper.toEntity(minorEventsSessionDTO);
        minorEventsSession = minorEventsSessionRepository.save(minorEventsSession);
        return minorEventsSessionMapper.toDto(minorEventsSession);
    }

    /**
     * Get all the minorEventsSessions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MinorEventsSessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MinorEventsSessions");
        return minorEventsSessionRepository.findAll(pageable)
            .map(minorEventsSessionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<MinorEventsSessionDTO> findAllBySessionId(Pageable pageable, Long sessionId) {
        log.debug("Request to get all MinorEventsSessions");
        return minorEventsSessionRepository.findAllBySessionId(pageable,sessionId)
            .map(minorEventsSessionMapper::toDto);
    }
    /**
     * Get one minorEventsSession by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MinorEventsSessionDTO> findOne(Long id) {
        log.debug("Request to get MinorEventsSession : {}", id);
        return minorEventsSessionRepository.findById(id)
            .map(minorEventsSessionMapper::toDto);
    }

    /**
     * Delete the minorEventsSession by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MinorEventsSession : {}", id);
        minorEventsSessionRepository.deleteById(id);
    }
}
