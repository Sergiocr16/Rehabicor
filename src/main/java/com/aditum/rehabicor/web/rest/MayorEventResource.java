package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.MayorEventService;
import com.aditum.rehabicor.service.PanelDataService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.MayorEventDTO;
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
 * REST controller for managing MayorEvent.
 */
@RestController
@RequestMapping("/api")
public class MayorEventResource {

    private final Logger log = LoggerFactory.getLogger(MayorEventResource.class);

    private static final String ENTITY_NAME = "mayorEvent";

    private final MayorEventService mayorEventService;

    private final PanelDataService panelDataService;


    public MayorEventResource(MayorEventService mayorEventService, PanelDataService panelDataService) {
        this.mayorEventService = mayorEventService;
        this.panelDataService = panelDataService;
    }


    /**
     * POST  /mayor-events : Create a new mayorEvent.
     *
     * @param mayorEventDTO the mayorEventDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mayorEventDTO, or with status 400 (Bad Request) if the mayorEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mayor-events")
    public ResponseEntity<MayorEventDTO> createMayorEvent(@Valid @RequestBody MayorEventDTO mayorEventDTO) throws URISyntaxException {
        log.debug("REST request to save MayorEvent : {}", mayorEventDTO);
        if (mayorEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new mayorEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MayorEventDTO result = mayorEventService.save(mayorEventDTO);
        return ResponseEntity.created(new URI("/api/mayor-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mayor-events : Updates an existing mayorEvent.
     *
     * @param mayorEventDTO the mayorEventDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mayorEventDTO,
     * or with status 400 (Bad Request) if the mayorEventDTO is not valid,
     * or with status 500 (Internal Server Error) if the mayorEventDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mayor-events")
    public ResponseEntity<MayorEventDTO> updateMayorEvent(@Valid @RequestBody MayorEventDTO mayorEventDTO) throws URISyntaxException {
        log.debug("REST request to update MayorEvent : {}", mayorEventDTO);
        if (mayorEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MayorEventDTO result = mayorEventService.save(mayorEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mayorEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mayor-events : get all the mayorEvents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mayorEvents in body
     */

    @GetMapping("/mayor-events")
    public ResponseEntity<List<MayorEventDTO>> getAllMayorEvents(Pageable pageable, Long rehabilitationId) {
        log.debug("REST request to get a page of MayorEvents");
        Page<MayorEventDTO> page = mayorEventService.findAll(pageable,rehabilitationId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mayor-events");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/mayor-events/graph")
    public ResponseEntity<List<MayorEventDTO>> getSessionsPerMayorEvent(Long groupId, Long rehabilitationId,Long mayorEventId) {
        log.debug("REST request to get a page of MayorEvents");
        List<MayorEventDTO> result = this.panelDataService.distributionMayorEventPerSessions(groupId,rehabilitationId,mayorEventId);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), null);
        return ResponseEntity.ok().headers(null).body(result);
    }
    /**
     * GET  /mayor-events/:id : get the "id" mayorEvent.
     *
     * @param id the id of the mayorEventDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mayorEventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mayor-events/{id}")
    public ResponseEntity<MayorEventDTO> getMayorEvent(@PathVariable Long id) {
        log.debug("REST request to get MayorEvent : {}", id);
        Optional<MayorEventDTO> mayorEventDTO = mayorEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mayorEventDTO);
    }

    /**
     * DELETE  /mayor-events/:id : delete the "id" mayorEvent.
     *
     * @param id the id of the mayorEventDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mayor-events/{id}")
    public ResponseEntity<Void> deleteMayorEvent(@PathVariable Long id) {
        log.debug("REST request to delete MayorEvent : {}", id);
        mayorEventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
