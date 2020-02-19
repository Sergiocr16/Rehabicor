package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.NonSpecificPainsSessionService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.NonSpecificPainsSessionDTO;
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
 * REST controller for managing NonSpecificPainsSession.
 */
@RestController
@RequestMapping("/api")
public class NonSpecificPainsSessionResource {

    private final Logger log = LoggerFactory.getLogger(NonSpecificPainsSessionResource.class);

    private static final String ENTITY_NAME = "nonSpecificPainsSession";

    private final NonSpecificPainsSessionService nonSpecificPainsSessionService;

    public NonSpecificPainsSessionResource(NonSpecificPainsSessionService nonSpecificPainsSessionService) {
        this.nonSpecificPainsSessionService = nonSpecificPainsSessionService;
    }

    /**
     * POST  /non-specific-pains-sessions : Create a new nonSpecificPainsSession.
     *
     * @param nonSpecificPainsSessionDTO the nonSpecificPainsSessionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nonSpecificPainsSessionDTO, or with status 400 (Bad Request) if the nonSpecificPainsSession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/non-specific-pains-sessions")
    public ResponseEntity<NonSpecificPainsSessionDTO> createNonSpecificPainsSession(@Valid @RequestBody NonSpecificPainsSessionDTO nonSpecificPainsSessionDTO) throws URISyntaxException {
        log.debug("REST request to save NonSpecificPainsSession : {}", nonSpecificPainsSessionDTO);
        if (nonSpecificPainsSessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new nonSpecificPainsSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NonSpecificPainsSessionDTO result = nonSpecificPainsSessionService.save(nonSpecificPainsSessionDTO);
        return ResponseEntity.created(new URI("/api/non-specific-pains-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /non-specific-pains-sessions : Updates an existing nonSpecificPainsSession.
     *
     * @param nonSpecificPainsSessionDTO the nonSpecificPainsSessionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nonSpecificPainsSessionDTO,
     * or with status 400 (Bad Request) if the nonSpecificPainsSessionDTO is not valid,
     * or with status 500 (Internal Server Error) if the nonSpecificPainsSessionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/non-specific-pains-sessions")
    public ResponseEntity<NonSpecificPainsSessionDTO> updateNonSpecificPainsSession(@Valid @RequestBody NonSpecificPainsSessionDTO nonSpecificPainsSessionDTO) throws URISyntaxException {
        log.debug("REST request to update NonSpecificPainsSession : {}", nonSpecificPainsSessionDTO);
        if (nonSpecificPainsSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NonSpecificPainsSessionDTO result = nonSpecificPainsSessionService.save(nonSpecificPainsSessionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nonSpecificPainsSessionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /non-specific-pains-sessions : get all the nonSpecificPainsSessions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nonSpecificPainsSessions in body
     */
    @GetMapping("/non-specific-pains-sessions")
    public ResponseEntity<List<NonSpecificPainsSessionDTO>> getAllNonSpecificPainsSessions(Pageable pageable) {
        log.debug("REST request to get a page of NonSpecificPainsSessions");
        Page<NonSpecificPainsSessionDTO> page = nonSpecificPainsSessionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/non-specific-pains-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /non-specific-pains-sessions/:id : get the "id" nonSpecificPainsSession.
     *
     * @param id the id of the nonSpecificPainsSessionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nonSpecificPainsSessionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/non-specific-pains-sessions/{id}")
    public ResponseEntity<NonSpecificPainsSessionDTO> getNonSpecificPainsSession(@PathVariable Long id) {
        log.debug("REST request to get NonSpecificPainsSession : {}", id);
        Optional<NonSpecificPainsSessionDTO> nonSpecificPainsSessionDTO = nonSpecificPainsSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nonSpecificPainsSessionDTO);
    }

    /**
     * DELETE  /non-specific-pains-sessions/:id : delete the "id" nonSpecificPainsSession.
     *
     * @param id the id of the nonSpecificPainsSessionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/non-specific-pains-sessions/{id}")
    public ResponseEntity<Void> deleteNonSpecificPainsSession(@PathVariable Long id) {
        log.debug("REST request to delete NonSpecificPainsSession : {}", id);
        nonSpecificPainsSessionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
