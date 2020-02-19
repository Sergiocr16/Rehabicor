package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.ComorbiditiesPatient;
import com.aditum.rehabicor.repository.ComorbiditiesPatientRepository;
import com.aditum.rehabicor.service.dto.ComorbiditiesPatientDTO;
import com.aditum.rehabicor.service.mapper.ComorbiditiesPatientMapper;
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
 * Service Implementation for managing ComorbiditiesPatient.
 */
@Service
@Transactional
public class ComorbiditiesPatientService {

    private final Logger log = LoggerFactory.getLogger(ComorbiditiesPatientService.class);

    private final ComorbiditiesPatientRepository comorbiditiesPatientRepository;

    private final ComorbiditiesPatientMapper comorbiditiesPatientMapper;

    public ComorbiditiesPatientService(ComorbiditiesPatientRepository comorbiditiesPatientRepository, ComorbiditiesPatientMapper comorbiditiesPatientMapper) {
        this.comorbiditiesPatientRepository = comorbiditiesPatientRepository;
        this.comorbiditiesPatientMapper = comorbiditiesPatientMapper;
    }

    /**
     * Save a comorbiditiesPatient.
     *
     * @param comorbiditiesPatientDTO the entity to save
     * @return the persisted entity
     */
    public ComorbiditiesPatientDTO save(ComorbiditiesPatientDTO comorbiditiesPatientDTO) {
        log.debug("Request to save ComorbiditiesPatient : {}", comorbiditiesPatientDTO);

        ComorbiditiesPatient comorbiditiesPatient = comorbiditiesPatientMapper.toEntity(comorbiditiesPatientDTO);
        Optional<ComorbiditiesPatient> comorbiditiesPatientOld = comorbiditiesPatientRepository.findFirstByInitialAssessmentIdAndComorbiditieRelation(comorbiditiesPatientDTO.getInitialAssessmentId(),comorbiditiesPatientDTO.getComorbiditieRelation()  );
        if(comorbiditiesPatientOld.isPresent()){
            this.delete(comorbiditiesPatientOld.get().getId());
        }
        comorbiditiesPatient = comorbiditiesPatientRepository.save(comorbiditiesPatient);

        return comorbiditiesPatientMapper.toDto(comorbiditiesPatient);
    }

    @Transactional(readOnly = true)
    public List<ComorbiditiesPatientDTO> findAllByInitialAsessment(Long initialAsessmentId) {
        log.debug("Request to get all ComorbiditiesPatients");
        List<ComorbiditiesPatientDTO> formatedComobitites = new ArrayList<>();
        comorbiditiesPatientRepository.findByInitialAssessmentId(initialAsessmentId).forEach(comorbiditiesPatient -> {
//            comorbiditiesPatient.setDescription(this.comorbiditieService.findOne(comorbiditiesPatient.getComorbiditietId()).get().getDescription());
            formatedComobitites.add(this.comorbiditiesPatientMapper.toDto(comorbiditiesPatient));
        });
        return formatedComobitites;
    }
    /**
     * Get all the comorbiditiesPatients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ComorbiditiesPatientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ComorbiditiesPatients");
        return comorbiditiesPatientRepository.findAll(pageable)
            .map(comorbiditiesPatientMapper::toDto);
    }


    /**
     * Get one comorbiditiesPatient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ComorbiditiesPatientDTO> findOne(Long id) {
        log.debug("Request to get ComorbiditiesPatient : {}", id);
        return comorbiditiesPatientRepository.findById(id)
            .map(comorbiditiesPatientMapper::toDto);
    }

    /**
     * Delete the comorbiditiesPatient by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ComorbiditiesPatient : {}", id);
        comorbiditiesPatientRepository.deleteById(id);
    }
}
