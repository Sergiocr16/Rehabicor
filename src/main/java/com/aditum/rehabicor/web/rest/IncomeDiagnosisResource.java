package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.IncomeDiagnosisService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.IncomeDiagnosisDTO;
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
 * REST controller for managing IncomeDiagnosis.
 */
@RestController
@RequestMapping("/api")
public class IncomeDiagnosisResource {

    private final Logger log = LoggerFactory.getLogger(IncomeDiagnosisResource.class);

    private static final String ENTITY_NAME = "incomeDiagnosis";

    private final IncomeDiagnosisService incomeDiagnosisService;

    public IncomeDiagnosisResource(IncomeDiagnosisService incomeDiagnosisService) {
        this.incomeDiagnosisService = incomeDiagnosisService;
    }

    /**
     * POST  /income-diagnoses : Create a new incomeDiagnosis.
     *
     * @param incomeDiagnosisDTO the incomeDiagnosisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incomeDiagnosisDTO, or with status 400 (Bad Request) if the incomeDiagnosis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/income-diagnoses")
    public ResponseEntity<IncomeDiagnosisDTO> createIncomeDiagnosis(@Valid @RequestBody IncomeDiagnosisDTO incomeDiagnosisDTO) throws URISyntaxException {
        log.debug("REST request to save IncomeDiagnosis : {}", incomeDiagnosisDTO);
        if (incomeDiagnosisDTO.getId() != null) {
            throw new BadRequestAlertException("A new incomeDiagnosis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncomeDiagnosisDTO result = incomeDiagnosisService.save(incomeDiagnosisDTO);
        return ResponseEntity.created(new URI("/api/income-diagnoses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /income-diagnoses : Updates an existing incomeDiagnosis.
     *
     * @param incomeDiagnosisDTO the incomeDiagnosisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incomeDiagnosisDTO,
     * or with status 400 (Bad Request) if the incomeDiagnosisDTO is not valid,
     * or with status 500 (Internal Server Error) if the incomeDiagnosisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/income-diagnoses")
    public ResponseEntity<IncomeDiagnosisDTO> updateIncomeDiagnosis(@Valid @RequestBody IncomeDiagnosisDTO incomeDiagnosisDTO) throws URISyntaxException {
        log.debug("REST request to update IncomeDiagnosis : {}", incomeDiagnosisDTO);
        if (incomeDiagnosisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncomeDiagnosisDTO result = incomeDiagnosisService.save(incomeDiagnosisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incomeDiagnosisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /income-diagnoses : get all the incomeDiagnoses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of incomeDiagnoses in body
     */
    @GetMapping("/income-diagnoses")
    public ResponseEntity<List<IncomeDiagnosisDTO>> getAllIncomeDiagnoses(Pageable pageable) {
        log.debug("REST request to get a page of IncomeDiagnoses");
        Page<IncomeDiagnosisDTO> page = incomeDiagnosisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/income-diagnoses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /income-diagnoses/:id : get the "id" incomeDiagnosis.
     *
     * @param id the id of the incomeDiagnosisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incomeDiagnosisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/income-diagnoses/{id}")
    public ResponseEntity<IncomeDiagnosisDTO> getIncomeDiagnosis(@PathVariable Long id) {
        log.debug("REST request to get IncomeDiagnosis : {}", id);
        Optional<IncomeDiagnosisDTO> incomeDiagnosisDTO = incomeDiagnosisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incomeDiagnosisDTO);
    }

    /**
     * DELETE  /income-diagnoses/:id : delete the "id" incomeDiagnosis.
     *
     * @param id the id of the incomeDiagnosisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/income-diagnoses/{id}")
    public ResponseEntity<Void> deleteIncomeDiagnosis(@PathVariable Long id) {
        log.debug("REST request to delete IncomeDiagnosis : {}", id);
        incomeDiagnosisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
