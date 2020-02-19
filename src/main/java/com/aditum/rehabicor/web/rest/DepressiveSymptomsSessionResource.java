package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.DepressiveSymptomsSessionService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.DepressiveSymptomsSessionDTO;
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
 * REST controller for managing DepressiveSymptomsSession.
 */
@RestController
@RequestMapping("/api")
public class DepressiveSymptomsSessionResource {

    private final Logger log = LoggerFactory.getLogger(DepressiveSymptomsSessionResource.class);

    private static final String ENTITY_NAME = "depressiveSymptomsSession";

    private final DepressiveSymptomsSessionService depressiveSymptomsSessionService;

    public DepressiveSymptomsSessionResource(DepressiveSymptomsSessionService depressiveSymptomsSessionService) {
        this.depressiveSymptomsSessionService = depressiveSymptomsSessionService;
    }

    /**
     * POST  /depressive-symptoms-sessions : Create a new depressiveSymptomsSession.
     *
     * @param depressiveSymptomsSessionDTO the depressiveSymptomsSessionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new depressiveSymptomsSessionDTO, or with status 400 (Bad Request) if the depressiveSymptomsSession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/depressive-symptoms-sessions")
    public ResponseEntity<DepressiveSymptomsSessionDTO> createDepressiveSymptomsSession(@Valid @RequestBody DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO) throws URISyntaxException {
        log.debug("REST request to save DepressiveSymptomsSession : {}", depressiveSymptomsSessionDTO);
        if (depressiveSymptomsSessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new depressiveSymptomsSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepressiveSymptomsSessionDTO result = depressiveSymptomsSessionService.save(depressiveSymptomsSessionDTO);
        return ResponseEntity.created(new URI("/api/depressive-symptoms-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /depressive-symptoms-sessions : Updates an existing depressiveSymptomsSession.
     *
     * @param depressiveSymptomsSessionDTO the depressiveSymptomsSessionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated depressiveSymptomsSessionDTO,
     * or with status 400 (Bad Request) if the depressiveSymptomsSessionDTO is not valid,
     * or with status 500 (Internal Server Error) if the depressiveSymptomsSessionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/depressive-symptoms-sessions")
    public ResponseEntity<DepressiveSymptomsSessionDTO> updateDepressiveSymptomsSession(@Valid @RequestBody DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO) throws URISyntaxException {
        log.debug("REST request to update DepressiveSymptomsSession : {}", depressiveSymptomsSessionDTO);
        if (depressiveSymptomsSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepressiveSymptomsSessionDTO result = depressiveSymptomsSessionService.save(depressiveSymptomsSessionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, depressiveSymptomsSessionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /depressive-symptoms-sessions : get all the depressiveSymptomsSessions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of depressiveSymptomsSessions in body
     */
    @GetMapping("/depressive-symptoms-sessions")
    public ResponseEntity<List<DepressiveSymptomsSessionDTO>> getAllDepressiveSymptomsSessions(Pageable pageable) {
        log.debug("REST request to get a page of DepressiveSymptomsSessions");
        Page<DepressiveSymptomsSessionDTO> page = depressiveSymptomsSessionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/depressive-symptoms-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/depressive-symptoms-sessions/by-session")
    public ResponseEntity<List<DepressiveSymptomsSessionDTO>> getAllDepressiveSymptomsSessionsBySession(Pageable pageable,Long sessionId) {
        log.debug("REST request to get a page of DepressiveSymptomsSessions");
        Page<DepressiveSymptomsSessionDTO> page = depressiveSymptomsSessionService.findAllBySession(pageable,sessionId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/depressive-symptoms-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * GET  /depressive-symptoms-sessions/:id : get the "id" depressiveSymptomsSession.
     *
     * @param id the id of the depressiveSymptomsSessionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the depressiveSymptomsSessionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/depressive-symptoms-sessions/{id}")
    public ResponseEntity<DepressiveSymptomsSessionDTO> getDepressiveSymptomsSession(@PathVariable Long id) {
        log.debug("REST request to get DepressiveSymptomsSession : {}", id);
        Optional<DepressiveSymptomsSessionDTO> depressiveSymptomsSessionDTO = depressiveSymptomsSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depressiveSymptomsSessionDTO);
    }

    /**
     * DELETE  /depressive-symptoms-sessions/:id : delete the "id" depressiveSymptomsSession.
     *
     * @param id the id of the depressiveSymptomsSessionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/depressive-symptoms-sessions/{id}")
    public ResponseEntity<Void> deleteDepressiveSymptomsSession(@PathVariable Long id) {
        log.debug("REST request to delete DepressiveSymptomsSession : {}", id);
        depressiveSymptomsSessionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
