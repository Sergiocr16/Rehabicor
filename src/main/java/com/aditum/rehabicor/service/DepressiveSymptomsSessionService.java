package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.DepressiveSymptomsSession;
import com.aditum.rehabicor.repository.DepressiveSymptomsSessionRepository;
import com.aditum.rehabicor.service.dto.DepressiveSymptomsSessionDTO;
import com.aditum.rehabicor.service.mapper.DepressiveSymptomsSessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DepressiveSymptomsSession.
 */
@Service
@Transactional
public class DepressiveSymptomsSessionService {

    private final Logger log = LoggerFactory.getLogger(DepressiveSymptomsSessionService.class);

    private final DepressiveSymptomsSessionRepository depressiveSymptomsSessionRepository;

    private final DepressiveSymptomsSessionMapper depressiveSymptomsSessionMapper;

    public DepressiveSymptomsSessionService(DepressiveSymptomsSessionRepository depressiveSymptomsSessionRepository, DepressiveSymptomsSessionMapper depressiveSymptomsSessionMapper) {
        this.depressiveSymptomsSessionRepository = depressiveSymptomsSessionRepository;
        this.depressiveSymptomsSessionMapper = depressiveSymptomsSessionMapper;
    }

    /**
     * Save a depressiveSymptomsSession.
     *
     * @param depressiveSymptomsSessionDTO the entity to save
     * @return the persisted entity
     */
    public DepressiveSymptomsSessionDTO save(DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO) {
        log.debug("Request to save DepressiveSymptomsSession : {}", depressiveSymptomsSessionDTO);
        DepressiveSymptomsSession depressiveSymptomsSession = depressiveSymptomsSessionMapper.toEntity(depressiveSymptomsSessionDTO);
        depressiveSymptomsSession = depressiveSymptomsSessionRepository.save(depressiveSymptomsSession);
        return depressiveSymptomsSessionMapper.toDto(depressiveSymptomsSession);
    }

    /**
     * Get all the depressiveSymptomsSessions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DepressiveSymptomsSessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DepressiveSymptomsSessions");
        return depressiveSymptomsSessionRepository.findAll(pageable)
            .map(depressiveSymptomsSessionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<DepressiveSymptomsSessionDTO> findAllBySession(Pageable pageable,Long sessionId) {
        log.debug("Request to get all DepressiveSymptomsSessions");
        return depressiveSymptomsSessionRepository.findAllBySessionId(pageable,sessionId)
            .map(depressiveSymptomsSessionMapper::toDto);
    }
    /**
     * Get one depressiveSymptomsSession by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DepressiveSymptomsSessionDTO> findOne(Long id) {
        log.debug("Request to get DepressiveSymptomsSession : {}", id);
        return depressiveSymptomsSessionRepository.findById(id)
            .map(depressiveSymptomsSessionMapper::toDto);
    }

    /**
     * Delete the depressiveSymptomsSession by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DepressiveSymptomsSession : {}", id);
        depressiveSymptomsSessionRepository.deleteById(id);
    }
}
