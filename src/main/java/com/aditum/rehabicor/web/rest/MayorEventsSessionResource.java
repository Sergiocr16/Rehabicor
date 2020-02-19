package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.MayorEventsSessionService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.MayorEventsSessionDTO;
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
 * REST controller for managing MayorEventsSession.
 */
@RestController
@RequestMapping("/api")
public class MayorEventsSessionResource {

    private final Logger log = LoggerFactory.getLogger(MayorEventsSessionResource.class);

    private static final String ENTITY_NAME = "mayorEventsSession";

    private final MayorEventsSessionService mayorEventsSessionService;

    public MayorEventsSessionResource(MayorEventsSessionService mayorEventsSessionService) {
        this.mayorEventsSessionService = mayorEventsSessionService;
    }

    /**
     * POST  /mayor-events-sessions : Create a new mayorEventsSession.
     *
     * @param mayorEventsSessionDTO the mayorEventsSessionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mayorEventsSessionDTO, or with status 400 (Bad Request) if the mayorEventsSession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mayor-events-sessions")
    public ResponseEntity<MayorEventsSessionDTO> createMayorEventsSession(@Valid @RequestBody MayorEventsSessionDTO mayorEventsSessionDTO) throws URISyntaxException {
        log.debug("REST request to save MayorEventsSession : {}", mayorEventsSessionDTO);
        if (mayorEventsSessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mayorEventsSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MayorEventsSessionDTO result = mayorEventsSessionService.save(mayorEventsSessionDTO);
        return ResponseEntity.created(new URI("/api/mayor-events-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mayor-events-sessions : Updates an existing mayorEventsSession.
     *
     * @param mayorEventsSessionDTO the mayorEventsSessionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mayorEventsSessionDTO,
     * or with status 400 (Bad Request) if the mayorEventsSessionDTO is not valid,
     * or with status 500 (Internal Server Error) if the mayorEventsSessionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mayor-events-sessions")
    public ResponseEntity<MayorEventsSessionDTO> updateMayorEventsSession(@Valid @RequestBody MayorEventsSessionDTO mayorEventsSessionDTO) throws URISyntaxException {
        log.debug("REST request to update MayorEventsSession : {}", mayorEventsSessionDTO);
        if (mayorEventsSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MayorEventsSessionDTO result = mayorEventsSessionService.save(mayorEventsSessionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mayorEventsSessionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mayor-events-sessions : get all the mayorEventsSessions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mayorEventsSessions in body
     */

    @GetMapping("/mayor-events-sessions")
    public ResponseEntity<List<MayorEventsSessionDTO>> getAllMayorEventsSessions(Pageable pageable) {
        log.debug("REST request to get a page of MayorEventsSessions");
        Page<MayorEventsSessionDTO> page = mayorEventsSessionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mayor-events-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/mayor-events-sessions/by-session")
    public ResponseEntity<List<MayorEventsSessionDTO>> getAllMayorEventsSessions(Pageable pageable,Long sessionId) {
        log.debug("REST request to get a page of MayorEventsSessions");
        Page<MayorEventsSessionDTO> page = mayorEventsSessionService.findAllBySession(pageable,sessionId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mayor-events-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    /**
     * GET  /mayor-events-sessions/:id : get the "id" mayorEventsSession.
     *
     * @param id the id of the mayorEventsSessionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mayorEventsSessionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mayor-events-sessions/{id}")
    public ResponseEntity<MayorEventsSessionDTO> getMayorEventsSession(@PathVariable Long id) {
        log.debug("REST request to get MayorEventsSession : {}", id);
        Optional<MayorEventsSessionDTO> mayorEventsSessionDTO = mayorEventsSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mayorEventsSessionDTO);
    }

    /**
     * DELETE  /mayor-events-sessions/:id : delete the "id" mayorEventsSession.
     *
     * @param id the id of the mayorEventsSessionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mayor-events-sessions/{id}")
    public ResponseEntity<Void> deleteMayorEventsSession(@PathVariable Long id) {
        log.debug("REST request to delete MayorEventsSession : {}", id);
        mayorEventsSessionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
