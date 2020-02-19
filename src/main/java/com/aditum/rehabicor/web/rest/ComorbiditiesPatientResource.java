package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.ComorbiditiesPatientService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.ComorbiditiesPatientDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ComorbiditiesPatient.
 */
@RestController
@RequestMapping("/api")
public class ComorbiditiesPatientResource {

    private final Logger log = LoggerFactory.getLogger(ComorbiditiesPatientResource.class);

    private static final String ENTITY_NAME = "comorbiditiesPatient";

    private final ComorbiditiesPatientService comorbiditiesPatientService;

    public ComorbiditiesPatientResource(ComorbiditiesPatientService comorbiditiesPatientService) {
        this.comorbiditiesPatientService = comorbiditiesPatientService;
    }

    /**
     * POST  /comorbidities-patients : Create a new comorbiditiesPatient.
     *
     * @param comorbiditiesPatientDTO the comorbiditiesPatientDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comorbiditiesPatientDTO, or with status 400 (Bad Request) if the comorbiditiesPatient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comorbidities-patients")
    public ResponseEntity<ComorbiditiesPatientDTO> createComorbiditiesPatient(@Valid @RequestBody ComorbiditiesPatientDTO comorbiditiesPatientDTO) throws URISyntaxException {
        log.debug("REST request to save ComorbiditiesPatient : {}", comorbiditiesPatientDTO);
        if (comorbiditiesPatientDTO.getId() != null) {
            throw new BadRequestAlertException("A new comorbiditiesPatient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComorbiditiesPatientDTO result = comorbiditiesPatientService.save(comorbiditiesPatientDTO);
        return ResponseEntity.created(new URI("/api/comorbidities-patients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comorbidities-patients : Updates an existing comorbiditiesPatient.
     *
     * @param comorbiditiesPatientDTO the comorbiditiesPatientDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comorbiditiesPatientDTO,
     * or with status 400 (Bad Request) if the comorbiditiesPatientDTO is not valid,
     * or with status 500 (Internal Server Error) if the comorbiditiesPatientDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comorbidities-patients")
    public ResponseEntity<ComorbiditiesPatientDTO> updateComorbiditiesPatient(@Valid @RequestBody ComorbiditiesPatientDTO comorbiditiesPatientDTO) throws URISyntaxException {
        log.debug("REST request to update ComorbiditiesPatient : {}", comorbiditiesPatientDTO);
        if (comorbiditiesPatientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComorbiditiesPatientDTO result = comorbiditiesPatientService.save(comorbiditiesPatientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comorbiditiesPatientDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comorbidities-patients : get all the comorbiditiesPatients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of comorbiditiesPatients in body
     */
    @GetMapping("/comorbidities-patients")
    public ResponseEntity<List<ComorbiditiesPatientDTO>> getAllComorbiditiesPatients(Pageable pageable) {
        log.debug("REST request to get a page of ComorbiditiesPatients");
        Page<ComorbiditiesPatientDTO> page = comorbiditiesPatientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comorbidities-patients");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/comorbidities-patients/by-asessment/")
    public ResponseEntity<List<ComorbiditiesPatientDTO>> getAllComorbiditiesPatients(Long id) {
        log.debug("REST request to get a page of ComorbiditiesPatients");
        List<ComorbiditiesPatientDTO> list = comorbiditiesPatientService.findAllByInitialAsessment(id);
        return ResponseEntity.ok().body(list);
    }
    /**
     * GET  /comorbidities-patients/:id : get the "id" comorbiditiesPatient.
     *
     * @param id the id of the comorbiditiesPatientDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comorbiditiesPatientDTO, or with status 404 (Not Found)
     */
    @GetMapping("/comorbidities-patients/{id}")
    public ResponseEntity<ComorbiditiesPatientDTO> getComorbiditiesPatient(@PathVariable Long id) {
        log.debug("REST request to get ComorbiditiesPatient : {}", id);
        Optional<ComorbiditiesPatientDTO> comorbiditiesPatientDTO = comorbiditiesPatientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comorbiditiesPatientDTO);
    }

    /**
     * DELETE  /comorbidities-patients/:id : delete the "id" comorbiditiesPatient.
     *
     * @param id the id of the comorbiditiesPatientDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comorbidities-patients/{id}")
    public ResponseEntity<Void> deleteComorbiditiesPatient(@PathVariable Long id) {
        log.debug("REST request to delete ComorbiditiesPatient : {}", id);
        comorbiditiesPatientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
