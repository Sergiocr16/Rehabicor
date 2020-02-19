package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.NonSpecificPainsSession;
import com.aditum.rehabicor.repository.NonSpecificPainsSessionRepository;
import com.aditum.rehabicor.service.dto.NonSpecificPainsSessionDTO;
import com.aditum.rehabicor.service.mapper.NonSpecificPainsSessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing NonSpecificPainsSession.
 */
@Service
@Transactional
public class NonSpecificPainsSessionService {

    private final Logger log = LoggerFactory.getLogger(NonSpecificPainsSessionService.class);

    private final NonSpecificPainsSessionRepository nonSpecificPainsSessionRepository;

    private final NonSpecificPainsSessionMapper nonSpecificPainsSessionMapper;

    public NonSpecificPainsSessionService(NonSpecificPainsSessionRepository nonSpecificPainsSessionRepository, NonSpecificPainsSessionMapper nonSpecificPainsSessionMapper) {
        this.nonSpecificPainsSessionRepository = nonSpecificPainsSessionRepository;
        this.nonSpecificPainsSessionMapper = nonSpecificPainsSessionMapper;
    }

    /**
     * Save a nonSpecificPainsSession.
     *
     * @param nonSpecificPainsSessionDTO the entity to save
     * @return the persisted entity
     */
    public NonSpecificPainsSessionDTO save(NonSpecificPainsSessionDTO nonSpecificPainsSessionDTO) {
        log.debug("Request to save NonSpecificPainsSession : {}", nonSpecificPainsSessionDTO);
        NonSpecificPainsSession nonSpecificPainsSession = nonSpecificPainsSessionMapper.toEntity(nonSpecificPainsSessionDTO);
        nonSpecificPainsSession = nonSpecificPainsSessionRepository.save(nonSpecificPainsSession);
        return nonSpecificPainsSessionMapper.toDto(nonSpecificPainsSession);
    }

    /**
     * Get all the nonSpecificPainsSessions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NonSpecificPainsSessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NonSpecificPainsSessions");
        return nonSpecificPainsSessionRepository.findAll(pageable)
            .map(nonSpecificPainsSessionMapper::toDto);
    }


    /**
     * Get one nonSpecificPainsSession by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NonSpecificPainsSessionDTO> findOne(Long id) {
        log.debug("Request to get NonSpecificPainsSession : {}", id);
        return nonSpecificPainsSessionRepository.findById(id)
            .map(nonSpecificPainsSessionMapper::toDto);
    }

    /**
     * Delete the nonSpecificPainsSession by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NonSpecificPainsSession : {}", id);
        nonSpecificPainsSessionRepository.deleteById(id);
    }
}
