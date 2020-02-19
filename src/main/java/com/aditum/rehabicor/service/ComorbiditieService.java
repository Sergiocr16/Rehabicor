package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.Comorbiditie;
import com.aditum.rehabicor.repository.ComorbiditieRepository;
import com.aditum.rehabicor.service.dto.ComorbiditieDTO;
import com.aditum.rehabicor.service.mapper.ComorbiditieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Comorbiditie.
 */
@Service
@Transactional
public class ComorbiditieService {

    private final Logger log = LoggerFactory.getLogger(ComorbiditieService.class);

    private final ComorbiditieRepository comorbiditieRepository;

    private final ComorbiditieMapper comorbiditieMapper;

    public ComorbiditieService(ComorbiditieRepository comorbiditieRepository, ComorbiditieMapper comorbiditieMapper) {
        this.comorbiditieRepository = comorbiditieRepository;
        this.comorbiditieMapper = comorbiditieMapper;
    }

    /**
     * Save a comorbiditie.
     *
     * @param comorbiditieDTO the entity to save
     * @return the persisted entity
     */
    public ComorbiditieDTO save(ComorbiditieDTO comorbiditieDTO) {
        log.debug("Request to save Comorbiditie : {}", comorbiditieDTO);
        Comorbiditie comorbiditie = comorbiditieMapper.toEntity(comorbiditieDTO);
        comorbiditie = comorbiditieRepository.save(comorbiditie);
        return comorbiditieMapper.toDto(comorbiditie);
    }

    /**
     * Get all the comorbidities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ComorbiditieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comorbidities");
        return comorbiditieRepository.findAll(pageable)
            .map(comorbiditieMapper::toDto);
    }


    /**
     * Get one comorbiditie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ComorbiditieDTO> findOne(Long id) {
        log.debug("Request to get Comorbiditie : {}", id);
        return comorbiditieRepository.findById(id)
            .map(comorbiditieMapper::toDto);
    }

    /**
     * Delete the comorbiditie by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Comorbiditie : {}", id);
        comorbiditieRepository.deleteById(id);
    }
}
