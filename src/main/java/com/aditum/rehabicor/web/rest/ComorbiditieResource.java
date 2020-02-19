package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.ComorbiditieService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.ComorbiditieDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Comorbiditie.
 */
@RestController
@RequestMapping("/api")
public class ComorbiditieResource {

    private final Logger log = LoggerFactory.getLogger(ComorbiditieResource.class);

    private static final String ENTITY_NAME = "comorbiditie";

    private final ComorbiditieService comorbiditieService;

    public ComorbiditieResource(ComorbiditieService comorbiditieService) {
        this.comorbiditieService = comorbiditieService;
    }

    /**
     * POST  /comorbidities : Create a new comorbiditie.
     *
     * @param comorbiditieDTO the comorbiditieDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comorbiditieDTO, or with status 400 (Bad Request) if the comorbiditie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comorbidities")
    public ResponseEntity<ComorbiditieDTO> createComorbiditie(@Valid @RequestBody ComorbiditieDTO comorbiditieDTO) throws URISyntaxException {
        log.debug("REST request to save Comorbiditie : {}", comorbiditieDTO);
        if (comorbiditieDTO.getId() != null) {
            throw new BadRequestAlertException("A new comorbiditie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComorbiditieDTO result = comorbiditieService.save(comorbiditieDTO);
        return ResponseEntity.created(new URI("/api/comorbidities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comorbidities : Updates an existing comorbiditie.
     *
     * @param comorbiditieDTO the comorbiditieDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comorbiditieDTO,
     * or with status 400 (Bad Request) if the comorbiditieDTO is not valid,
     * or with status 500 (Internal Server Error) if the comorbiditieDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comorbidities")
    public ResponseEntity<ComorbiditieDTO> updateComorbiditie(@Valid @RequestBody ComorbiditieDTO comorbiditieDTO) throws URISyntaxException {
        log.debug("REST request to update Comorbiditie : {}", comorbiditieDTO);
        if (comorbiditieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComorbiditieDTO result = comorbiditieService.save(comorbiditieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comorbiditieDTO.getId().toString()))
            .body(result);
    }


    @GetMapping("/comorbidities")
    public ResponseEntity<List<ComorbiditieDTO>> getAllComorbidities(Pageable pageable, Long rehabilitationId) {
        log.debug("REST request to get a page of Comorbidities");
        Page<ComorbiditieDTO> page = comorbiditieService.findAll(pageable,rehabilitationId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,"/api/comorbidities");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    /**
     * GET  /comorbidities/:id : get the "id" comorbiditie.
     *
     * @param id the id of the comorbiditieDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comorbiditieDTO, or with status 404 (Not Found)
     */
    @GetMapping("/comorbidities/{id}")
    public ResponseEntity<ComorbiditieDTO> getComorbiditie(@PathVariable Long id) {
        log.debug("REST request to get Comorbiditie : {}", id);
        Optional<ComorbiditieDTO> comorbiditieDTO = comorbiditieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comorbiditieDTO);
    }

    /**
     * DELETE  /comorbidities/:id : delete the "id" comorbiditie.
     *
     * @param id the id of the comorbiditieDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comorbidities/{id}")
    public ResponseEntity<Void> deleteComorbiditie(@PathVariable Long id) {
        log.debug("REST request to delete Comorbiditie : {}", id);
        comorbiditieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
