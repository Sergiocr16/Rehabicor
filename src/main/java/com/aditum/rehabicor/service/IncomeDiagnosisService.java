package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.IncomeDiagnosis;
import com.aditum.rehabicor.repository.IncomeDiagnosisRepository;
import com.aditum.rehabicor.service.dto.IncomeDiagnosisDTO;
import com.aditum.rehabicor.service.mapper.IncomeDiagnosisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing IncomeDiagnosis.
 */
@Service
@Transactional
public class IncomeDiagnosisService {

    private final Logger log = LoggerFactory.getLogger(IncomeDiagnosisService.class);

    private final IncomeDiagnosisRepository incomeDiagnosisRepository;

    private final IncomeDiagnosisMapper incomeDiagnosisMapper;

    public IncomeDiagnosisService(IncomeDiagnosisRepository incomeDiagnosisRepository, IncomeDiagnosisMapper incomeDiagnosisMapper) {
        this.incomeDiagnosisRepository = incomeDiagnosisRepository;
        this.incomeDiagnosisMapper = incomeDiagnosisMapper;
    }

    /**
     * Save a incomeDiagnosis.
     *
     * @param incomeDiagnosisDTO the entity to save
     * @return the persisted entity
     */
    public IncomeDiagnosisDTO save(IncomeDiagnosisDTO incomeDiagnosisDTO) {
        log.debug("Request to save IncomeDiagnosis : {}", incomeDiagnosisDTO);
        IncomeDiagnosis incomeDiagnosis = incomeDiagnosisMapper.toEntity(incomeDiagnosisDTO);
        incomeDiagnosis = incomeDiagnosisRepository.save(incomeDiagnosis);
        return incomeDiagnosisMapper.toDto(incomeDiagnosis);
    }

    /**
     * Get all the incomeDiagnoses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IncomeDiagnosisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncomeDiagnoses");
        return incomeDiagnosisRepository.findAll(pageable)
            .map(incomeDiagnosisMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<IncomeDiagnosisDTO> findAll(Pageable pageable, Long rehabilitationId) {
        log.debug("Request to get all IncomeDiagnoses");
        return incomeDiagnosisRepository.findByRehabilitationCenterIdAndAndDeleted(pageable, rehabilitationId, false)
            .map(incomeDiagnosisMapper::toDto);
    }
    @Transactional(readOnly = true)
    public List<IncomeDiagnosisDTO> findAllNoPage(Long rehabilitationId) {
        log.debug("Request to get all IncomeDiagnoses");
        List<IncomeDiagnosisDTO> incomeDiagnosisDTOS = new ArrayList<>();
        incomeDiagnosisRepository.findByRehabilitationCenterIdAndAndDeleted(rehabilitationId, false).forEach(
            incomeDiagnosis -> {
                incomeDiagnosisDTOS.add(this.incomeDiagnosisMapper.toDto(incomeDiagnosis));
            }
        );
        return incomeDiagnosisDTOS;
    }

    /**
     * Get one incomeDiagnosis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<IncomeDiagnosisDTO> findOne(Long id) {
        log.debug("Request to get IncomeDiagnosis : {}", id);
        return incomeDiagnosisRepository.findById(id)
            .map(incomeDiagnosisMapper::toDto);
    }

    /**
     * Delete the incomeDiagnosis by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IncomeDiagnosis : {}", id);
        incomeDiagnosisRepository.deleteById(id);
    }
}
