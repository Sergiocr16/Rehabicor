package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.NonSpecificPainService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.NonSpecificPainDTO;
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
 * REST controller for managing NonSpecificPain.
 */
@RestController
@RequestMapping("/api")
public class NonSpecificPainResource {

    private final Logger log = LoggerFactory.getLogger(NonSpecificPainResource.class);

    private static final String ENTITY_NAME = "nonSpecificPain";

    private final NonSpecificPainService nonSpecificPainService;

    public NonSpecificPainResource(NonSpecificPainService nonSpecificPainService) {
        this.nonSpecificPainService = nonSpecificPainService;
    }

    /**
     * POST  /non-specific-pains : Create a new nonSpecificPain.
     *
     * @param nonSpecificPainDTO the nonSpecificPainDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nonSpecificPainDTO, or with status 400 (Bad Request) if the nonSpecificPain has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/non-specific-pains")
    public ResponseEntity<NonSpecificPainDTO> createNonSpecificPain(@Valid @RequestBody NonSpecificPainDTO nonSpecificPainDTO) throws URISyntaxException {
        log.debug("REST request to save NonSpecificPain : {}", nonSpecificPainDTO);
        if (nonSpecificPainDTO.getId() != null) {
            throw new BadRequestAlertException("A new nonSpecificPain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NonSpecificPainDTO result = nonSpecificPainService.save(nonSpecificPainDTO);
        return ResponseEntity.created(new URI("/api/non-specific-pains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /non-specific-pains : Updates an existing nonSpecificPain.
     *
     * @param nonSpecificPainDTO the nonSpecificPainDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nonSpecificPainDTO,
     * or with status 400 (Bad Request) if the nonSpecificPainDTO is not valid,
     * or with status 500 (Internal Server Error) if the nonSpecificPainDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/non-specific-pains")
    public ResponseEntity<NonSpecificPainDTO> updateNonSpecificPain(@Valid @RequestBody NonSpecificPainDTO nonSpecificPainDTO) throws URISyntaxException {
        log.debug("REST request to update NonSpecificPain : {}", nonSpecificPainDTO);
        if (nonSpecificPainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NonSpecificPainDTO result = nonSpecificPainService.save(nonSpecificPainDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nonSpecificPainDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /non-specific-pains : get all the nonSpecificPains.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nonSpecificPains in body
     */

    @GetMapping("/non-specific-pains")
    public ResponseEntity<List<NonSpecificPainDTO>> getAllNonSpecificPains(Pageable pageable, Long rehabilitationId) {
        log.debug("REST request to get a page of NonSpecificPains");
        Page<NonSpecificPainDTO> page = nonSpecificPainService.findAll(pageable,rehabilitationId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/non-specific-pains");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    /**
     * GET  /non-specific-pains/:id : get the "id" nonSpecificPain.
     *
     * @param id the id of the nonSpecificPainDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nonSpecificPainDTO, or with status 404 (Not Found)
     */
    @GetMapping("/non-specific-pains/{id}")
    public ResponseEntity<NonSpecificPainDTO> getNonSpecificPain(@PathVariable Long id) {
        log.debug("REST request to get NonSpecificPain : {}", id);
        Optional<NonSpecificPainDTO> nonSpecificPainDTO = nonSpecificPainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nonSpecificPainDTO);
    }

    /**
     * DELETE  /non-specific-pains/:id : delete the "id" nonSpecificPain.
     *
     * @param id the id of the nonSpecificPainDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/non-specific-pains/{id}")
    public ResponseEntity<Void> deleteNonSpecificPain(@PathVariable Long id) {
        log.debug("REST request to delete NonSpecificPain : {}", id);
        nonSpecificPainService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
