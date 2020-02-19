package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.FinalAssessment;
import com.aditum.rehabicor.repository.FinalAssessmentRepository;
import com.aditum.rehabicor.service.dto.FinalAssessmentDTO;
import com.aditum.rehabicor.service.mapper.FinalAssessmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing FinalAssessment.
 */
@Service
@Transactional
public class FinalAssessmentService {

    private final Logger log = LoggerFactory.getLogger(FinalAssessmentService.class);

    private final FinalAssessmentRepository finalAssessmentRepository;

    private final FinalAssessmentMapper finalAssessmentMapper;

    public FinalAssessmentService(FinalAssessmentRepository finalAssessmentRepository, FinalAssessmentMapper finalAssessmentMapper) {
        this.finalAssessmentRepository = finalAssessmentRepository;
        this.finalAssessmentMapper = finalAssessmentMapper;
    }

    /**
     * Save a finalAssessment.
     *
     * @param finalAssessmentDTO the entity to save
     * @return the persisted entity
     */
    public FinalAssessmentDTO save(FinalAssessmentDTO finalAssessmentDTO) {
        log.debug("Request to save FinalAssessment : {}", finalAssessmentDTO);
        FinalAssessment finalAssessment = finalAssessmentMapper.toEntity(finalAssessmentDTO);
        finalAssessment = finalAssessmentRepository.save(finalAssessment);
        return finalAssessmentMapper.toDto(finalAssessment);
    }

    /**
     * Get all the finalAssessments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FinalAssessmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FinalAssessments");
        return finalAssessmentRepository.findAll(pageable)
            .map(finalAssessmentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<FinalAssessmentDTO> findAllByPatient(Pageable pageable, Long patientId) {
        log.debug("Request to get all FinalAssessments");
        return finalAssessmentRepository.findAllByPatientId(pageable, patientId)
            .map(finalAssessmentMapper::toDto);
    }
    /**
     * Get one finalAssessment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FinalAssessmentDTO> findOne(Long id) {
        log.debug("Request to get FinalAssessment : {}", id);
        return finalAssessmentRepository.findById(id)
            .map(finalAssessmentMapper::toDto);
    }

    /**
     * Delete the finalAssessment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FinalAssessment : {}", id);
        finalAssessmentRepository.deleteById(id);
    }
}
