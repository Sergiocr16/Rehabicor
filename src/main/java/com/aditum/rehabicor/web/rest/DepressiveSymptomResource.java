package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.DepressiveSymptomService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.DepressiveSymptomDTO;
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
 * REST controller for managing DepressiveSymptom.
 */
@RestController
@RequestMapping("/api")
public class DepressiveSymptomResource {

    private final Logger log = LoggerFactory.getLogger(DepressiveSymptomResource.class);

    private static final String ENTITY_NAME = "depressiveSymptom";

    private final DepressiveSymptomService depressiveSymptomService;

    public DepressiveSymptomResource(DepressiveSymptomService depressiveSymptomService) {
        this.depressiveSymptomService = depressiveSymptomService;
    }

    /**
     * POST  /depressive-symptoms : Create a new depressiveSymptom.
     *
     * @param depressiveSymptomDTO the depressiveSymptomDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new depressiveSymptomDTO, or with status 400 (Bad Request) if the depressiveSymptom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/depressive-symptoms")
    public ResponseEntity<DepressiveSymptomDTO> createDepressiveSymptom(@Valid @RequestBody DepressiveSymptomDTO depressiveSymptomDTO) throws URISyntaxException {
        log.debug("REST request to save DepressiveSymptom : {}", depressiveSymptomDTO);
        if (depressiveSymptomDTO.getId() != null) {
            throw new BadRequestAlertException("A new depressiveSymptom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepressiveSymptomDTO result = depressiveSymptomService.save(depressiveSymptomDTO);
        return ResponseEntity.created(new URI("/api/depressive-symptoms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /depressive-symptoms : Updates an existing depressiveSymptom.
     *
     * @param depressiveSymptomDTO the depressiveSymptomDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated depressiveSymptomDTO,
     * or with status 400 (Bad Request) if the depressiveSymptomDTO is not valid,
     * or with status 500 (Internal Server Error) if the depressiveSymptomDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/depressive-symptoms")
    public ResponseEntity<DepressiveSymptomDTO> updateDepressiveSymptom(@Valid @RequestBody DepressiveSymptomDTO depressiveSymptomDTO) throws URISyntaxException {
        log.debug("REST request to update DepressiveSymptom : {}", depressiveSymptomDTO);
        if (depressiveSymptomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepressiveSymptomDTO result = depressiveSymptomService.save(depressiveSymptomDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, depressiveSymptomDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /depressive-symptoms : get all the depressiveSymptoms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of depressiveSymptoms in body
     */
    @GetMapping("/depressive-symptoms")
    public ResponseEntity<List<DepressiveSymptomDTO>> getAllDepressiveSymptoms(Pageable pageable) {
        log.debug("REST request to get a page of DepressiveSymptoms");
        Page<DepressiveSymptomDTO> page = depressiveSymptomService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/depressive-symptoms");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /depressive-symptoms/:id : get the "id" depressiveSymptom.
     *
     * @param id the id of the depressiveSymptomDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the depressiveSymptomDTO, or with status 404 (Not Found)
     */
    @GetMapping("/depressive-symptoms/{id}")
    public ResponseEntity<DepressiveSymptomDTO> getDepressiveSymptom(@PathVariable Long id) {
        log.debug("REST request to get DepressiveSymptom : {}", id);
        Optional<DepressiveSymptomDTO> depressiveSymptomDTO = depressiveSymptomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depressiveSymptomDTO);
    }

    /**
     * DELETE  /depressive-symptoms/:id : delete the "id" depressiveSymptom.
     *
     * @param id the id of the depressiveSymptomDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/depressive-symptoms/{id}")
    public ResponseEntity<Void> deleteDepressiveSymptom(@PathVariable Long id) {
        log.debug("REST request to delete DepressiveSymptom : {}", id);
        depressiveSymptomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
