package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.DepressiveSymptom;
import com.aditum.rehabicor.repository.DepressiveSymptomRepository;
import com.aditum.rehabicor.service.dto.DepressiveSymptomDTO;
import com.aditum.rehabicor.service.mapper.DepressiveSymptomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DepressiveSymptom.
 */
@Service
@Transactional
public class DepressiveSymptomService {

    private final Logger log = LoggerFactory.getLogger(DepressiveSymptomService.class);

    private final DepressiveSymptomRepository depressiveSymptomRepository;

    private final DepressiveSymptomMapper depressiveSymptomMapper;

    public DepressiveSymptomService(DepressiveSymptomRepository depressiveSymptomRepository, DepressiveSymptomMapper depressiveSymptomMapper) {
        this.depressiveSymptomRepository = depressiveSymptomRepository;
        this.depressiveSymptomMapper = depressiveSymptomMapper;
    }

    /**
     * Save a depressiveSymptom.
     *
     * @param depressiveSymptomDTO the entity to save
     * @return the persisted entity
     */
    public DepressiveSymptomDTO save(DepressiveSymptomDTO depressiveSymptomDTO) {
        log.debug("Request to save DepressiveSymptom : {}", depressiveSymptomDTO);
        DepressiveSymptom depressiveSymptom = depressiveSymptomMapper.toEntity(depressiveSymptomDTO);
        depressiveSymptom = depressiveSymptomRepository.save(depressiveSymptom);
        return depressiveSymptomMapper.toDto(depressiveSymptom);
    }

    /**
     * Get all the depressiveSymptoms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DepressiveSymptomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DepressiveSymptoms");
        return depressiveSymptomRepository.findAll(pageable)
            .map(depressiveSymptomMapper::toDto);
    }


    /**
     * Get one depressiveSymptom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DepressiveSymptomDTO> findOne(Long id) {
        log.debug("Request to get DepressiveSymptom : {}", id);
        return depressiveSymptomRepository.findById(id)
            .map(depressiveSymptomMapper::toDto);
    }

    /**
     * Delete the depressiveSymptom by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DepressiveSymptom : {}", id);
        depressiveSymptomRepository.deleteById(id);
    }
}
