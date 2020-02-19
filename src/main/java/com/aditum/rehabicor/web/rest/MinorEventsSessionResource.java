package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.MinorEventsSessionService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.MinorEventsSessionDTO;
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
 * REST controller for managing MinorEventsSession.
 */
@RestController
@RequestMapping("/api")
public class MinorEventsSessionResource {

    private final Logger log = LoggerFactory.getLogger(MinorEventsSessionResource.class);

    private static final String ENTITY_NAME = "minorEventsSession";

    private final MinorEventsSessionService minorEventsSessionService;

    public MinorEventsSessionResource(MinorEventsSessionService minorEventsSessionService) {
        this.minorEventsSessionService = minorEventsSessionService;
    }

    /**
     * POST  /minor-events-sessions : Create a new minorEventsSession.
     *
     * @param minorEventsSessionDTO the minorEventsSessionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new minorEventsSessionDTO, or with status 400 (Bad Request) if the minorEventsSession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/minor-events-sessions")
    public ResponseEntity<MinorEventsSessionDTO> createMinorEventsSession(@Valid @RequestBody MinorEventsSessionDTO minorEventsSessionDTO) throws URISyntaxException {
        log.debug("REST request to save MinorEventsSession : {}", minorEventsSessionDTO);
        if (minorEventsSessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new minorEventsSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MinorEventsSessionDTO result = minorEventsSessionService.save(minorEventsSessionDTO);
        return ResponseEntity.created(new URI("/api/minor-events-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /minor-events-sessions : Updates an existing minorEventsSession.
     *
     * @param minorEventsSessionDTO the minorEventsSessionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated minorEventsSessionDTO,
     * or with status 400 (Bad Request) if the minorEventsSessionDTO is not valid,
     * or with status 500 (Internal Server Error) if the minorEventsSessionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/minor-events-sessions")
    public ResponseEntity<MinorEventsSessionDTO> updateMinorEventsSession(@Valid @RequestBody MinorEventsSessionDTO minorEventsSessionDTO) throws URISyntaxException {
        log.debug("REST request to update MinorEventsSession : {}", minorEventsSessionDTO);
        if (minorEventsSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MinorEventsSessionDTO result = minorEventsSessionService.save(minorEventsSessionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, minorEventsSessionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /minor-events-sessions : get all the minorEventsSessions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of minorEventsSessions in body
     */
    @GetMapping("/minor-events-sessions")
    public ResponseEntity<List<MinorEventsSessionDTO>> getAllMinorEventsSessions(Pageable pageable) {
        log.debug("REST request to get a page of MinorEventsSessions");
        Page<MinorEventsSessionDTO> page = minorEventsSessionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/minor-events-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /minor-events-sessions/:id : get the "id" minorEventsSession.
     *
     * @param id the id of the minorEventsSessionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the minorEventsSessionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/minor-events-sessions/{id}")
    public ResponseEntity<MinorEventsSessionDTO> getMinorEventsSession(@PathVariable Long id) {
        log.debug("REST request to get MinorEventsSession : {}", id);
        Optional<MinorEventsSessionDTO> minorEventsSessionDTO = minorEventsSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(minorEventsSessionDTO);
    }

    /**
     * DELETE  /minor-events-sessions/:id : delete the "id" minorEventsSession.
     *
     * @param id the id of the minorEventsSessionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/minor-events-sessions/{id}")
    public ResponseEntity<Void> deleteMinorEventsSession(@PathVariable Long id) {
        log.debug("REST request to delete MinorEventsSession : {}", id);
        minorEventsSessionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
