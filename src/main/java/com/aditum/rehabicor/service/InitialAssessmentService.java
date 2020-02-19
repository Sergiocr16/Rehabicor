package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.InitialAssessment;
import com.aditum.rehabicor.repository.InitialAssessmentRepository;
import com.aditum.rehabicor.service.dto.InitialAssessmentDTO;
import com.aditum.rehabicor.service.mapper.InitialAssessmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing InitialAssessment.
 */
@Service
@Transactional
public class InitialAssessmentService {

    private final Logger log = LoggerFactory.getLogger(InitialAssessmentService.class);

    private final InitialAssessmentRepository initialAssessmentRepository;

    private final InitialAssessmentMapper initialAssessmentMapper;

    public InitialAssessmentService(InitialAssessmentRepository initialAssessmentRepository, InitialAssessmentMapper initialAssessmentMapper) {
        this.initialAssessmentRepository = initialAssessmentRepository;
        this.initialAssessmentMapper = initialAssessmentMapper;
    }

    /**
     * Save a initialAssessment.
     *
     * @param initialAssessmentDTO the entity to save
     * @return the persisted entity
     */
    public InitialAssessmentDTO save(InitialAssessmentDTO initialAssessmentDTO) {
        log.debug("Request to save InitialAssessment : {}", initialAssessmentDTO);
        InitialAssessment initialAssessment = initialAssessmentMapper.toEntity(initialAssessmentDTO);
        initialAssessment = initialAssessmentRepository.save(initialAssessment);
        return initialAssessmentMapper.toDto(initialAssessment);
    }
    @Transactional(readOnly = true)
    public Optional<InitialAssessmentDTO> findOneByPatient(Long patientId) {
        log.debug("Request to get InitialAssessment : {}", patientId);
        return initialAssessmentRepository.findByPatientId(patientId)
            .map(initialAssessmentMapper::toDto);
    }
    /**
     * Get all the initialAssessments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InitialAssessmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InitialAssessments");
        return initialAssessmentRepository.findAll(pageable)
            .map(initialAssessmentMapper::toDto);
    }


    /**
     * Get one initialAssessment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InitialAssessmentDTO> findOne(Long id) {
        log.debug("Request to get InitialAssessment : {}", id);
        return initialAssessmentRepository.findById(id)
            .map(initialAssessmentMapper::toDto);
    }

    /**
     * Delete the initialAssessment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InitialAssessment : {}", id);
        initialAssessmentRepository.deleteById(id);
    }
}
