package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.MinorEventService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.MinorEventDTO;
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
 * REST controller for managing MinorEvent.
 */
@RestController
@RequestMapping("/api")
public class MinorEventResource {

    private final Logger log = LoggerFactory.getLogger(MinorEventResource.class);

    private static final String ENTITY_NAME = "minorEvent";

    private final MinorEventService minorEventService;

    public MinorEventResource(MinorEventService minorEventService) {
        this.minorEventService = minorEventService;
    }

    /**
     * POST  /minor-events : Create a new minorEvent.
     *
     * @param minorEventDTO the minorEventDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new minorEventDTO, or with status 400 (Bad Request) if the minorEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/minor-events")
    public ResponseEntity<MinorEventDTO> createMinorEvent(@Valid @RequestBody MinorEventDTO minorEventDTO) throws URISyntaxException {
        log.debug("REST request to save MinorEvent : {}", minorEventDTO);
        if (minorEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new minorEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MinorEventDTO result = minorEventService.save(minorEventDTO);
        return ResponseEntity.created(new URI("/api/minor-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /minor-events : Updates an existing minorEvent.
     *
     * @param minorEventDTO the minorEventDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated minorEventDTO,
     * or with status 400 (Bad Request) if the minorEventDTO is not valid,
     * or with status 500 (Internal Server Error) if the minorEventDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/minor-events")
    public ResponseEntity<MinorEventDTO> updateMinorEvent(@Valid @RequestBody MinorEventDTO minorEventDTO) throws URISyntaxException {
        log.debug("REST request to update MinorEvent : {}", minorEventDTO);
        if (minorEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MinorEventDTO result = minorEventService.save(minorEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, minorEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /minor-events : get all the minorEvents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of minorEvents in body
     */
    @GetMapping("/minor-events")
    public ResponseEntity<List<MinorEventDTO>> getAllMinorEvents(Pageable pageable) {
        log.debug("REST request to get a page of MinorEvents");
        Page<MinorEventDTO> page = minorEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/minor-events");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /minor-events/:id : get the "id" minorEvent.
     *
     * @param id the id of the minorEventDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the minorEventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/minor-events/{id}")
    public ResponseEntity<MinorEventDTO> getMinorEvent(@PathVariable Long id) {
        log.debug("REST request to get MinorEvent : {}", id);
        Optional<MinorEventDTO> minorEventDTO = minorEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(minorEventDTO);
    }

    /**
     * DELETE  /minor-events/:id : delete the "id" minorEvent.
     *
     * @param id the id of the minorEventDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/minor-events/{id}")
    public ResponseEntity<Void> deleteMinorEvent(@PathVariable Long id) {
        log.debug("REST request to delete MinorEvent : {}", id);
        minorEventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
