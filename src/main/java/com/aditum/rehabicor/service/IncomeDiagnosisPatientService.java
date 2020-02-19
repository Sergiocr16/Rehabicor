package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.IncomeDiagnosisPatient;
import com.aditum.rehabicor.repository.IncomeDiagnosisPatientRepository;
import com.aditum.rehabicor.service.dto.IncomeDiagnosisPatientDTO;
import com.aditum.rehabicor.service.mapper.IncomeDiagnosisPatientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing IncomeDiagnosisPatient.
 */
@Service
@Transactional
public class IncomeDiagnosisPatientService {

    private final Logger log = LoggerFactory.getLogger(IncomeDiagnosisPatientService.class);

    private final IncomeDiagnosisPatientRepository incomeDiagnosisPatientRepository;

    private final IncomeDiagnosisPatientMapper incomeDiagnosisPatientMapper;

    public IncomeDiagnosisPatientService(IncomeDiagnosisPatientRepository incomeDiagnosisPatientRepository, IncomeDiagnosisPatientMapper incomeDiagnosisPatientMapper) {
        this.incomeDiagnosisPatientRepository = incomeDiagnosisPatientRepository;
        this.incomeDiagnosisPatientMapper = incomeDiagnosisPatientMapper;
    }

    /**
     * Save a incomeDiagnosisPatient.
     *
     * @param incomeDiagnosisPatientDTO the entity to save
     * @return the persisted entity
     */
    public IncomeDiagnosisPatientDTO save(IncomeDiagnosisPatientDTO incomeDiagnosisPatientDTO) {
        log.debug("Request to save IncomeDiagnosisPatient : {}", incomeDiagnosisPatientDTO);
        IncomeDiagnosisPatient incomeDiagnosisPatient = incomeDiagnosisPatientMapper.toEntity(incomeDiagnosisPatientDTO);
        incomeDiagnosisPatient = incomeDiagnosisPatientRepository.save(incomeDiagnosisPatient);
        return incomeDiagnosisPatientMapper.toDto(incomeDiagnosisPatient);
    }

    /**
     * Get all the incomeDiagnosisPatients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IncomeDiagnosisPatientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncomeDiagnosisPatients");
        return incomeDiagnosisPatientRepository.findAll(pageable)
            .map(incomeDiagnosisPatientMapper::toDto);
    }


    /**
     * Get one incomeDiagnosisPatient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<IncomeDiagnosisPatientDTO> findOne(Long id) {
        log.debug("Request to get IncomeDiagnosisPatient : {}", id);
        return incomeDiagnosisPatientRepository.findById(id)
            .map(incomeDiagnosisPatientMapper::toDto);
    }

    /**
     * Delete the incomeDiagnosisPatient by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IncomeDiagnosisPatient : {}", id);
        incomeDiagnosisPatientRepository.deleteById(id);
    }
}
