package com.aditum.rehabicor.web.rest;
import com.aditum.rehabicor.service.RehabilitationGroupService;
import com.aditum.rehabicor.web.rest.errors.BadRequestAlertException;
import com.aditum.rehabicor.web.rest.util.HeaderUtil;
import com.aditum.rehabicor.web.rest.util.PaginationUtil;
import com.aditum.rehabicor.service.dto.RehabilitationGroupDTO;
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
 * REST controller for managing RehabilitationGroup.
 */
@RestController
@RequestMapping("/api")
public class RehabilitationGroupResource {

    private final Logger log = LoggerFactory.getLogger(RehabilitationGroupResource.class);

    private static final String ENTITY_NAME = "rehabilitationGroup";

    private final RehabilitationGroupService rehabilitationGroupService;

    public RehabilitationGroupResource(RehabilitationGroupService rehabilitationGroupService) {
        this.rehabilitationGroupService = rehabilitationGroupService;
    }

    /**
     * POST  /rehabilitation-groups : Create a new rehabilitationGroup.
     *
     * @param rehabilitationGroupDTO the rehabilitationGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rehabilitationGroupDTO, or with status 400 (Bad Request) if the rehabilitationGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rehabilitation-groups")
    public ResponseEntity<RehabilitationGroupDTO> createRehabilitationGroup(@Valid @RequestBody RehabilitationGroupDTO rehabilitationGroupDTO) throws URISyntaxException {
        log.debug("REST request to save RehabilitationGroup : {}", rehabilitationGroupDTO);
        if (rehabilitationGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new rehabilitationGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RehabilitationGroupDTO result = rehabilitationGroupService.save(rehabilitationGroupDTO);
        return ResponseEntity.created(new URI("/api/rehabilitation-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rehabilitation-groups : Updates an existing rehabilitationGroup.
     *
     * @param rehabilitationGroupDTO the rehabilitationGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rehabilitationGroupDTO,
     * or with status 400 (Bad Request) if the rehabilitationGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the rehabilitationGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rehabilitation-groups")
    public ResponseEntity<RehabilitationGroupDTO> updateRehabilitationGroup(@Valid @RequestBody RehabilitationGroupDTO rehabilitationGroupDTO) throws URISyntaxException {
        log.debug("REST request to update RehabilitationGroup : {}", rehabilitationGroupDTO);
        if (rehabilitationGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RehabilitationGroupDTO result = rehabilitationGroupService.save(rehabilitationGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rehabilitationGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rehabilitation-groups : get all the rehabilitationGroups.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of rehabilitationGroups in body
     */
    @GetMapping("/rehabilitation-groups")
    public ResponseEntity<List<RehabilitationGroupDTO>> getAllRehabilitationGroups(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of RehabilitationGroups");
        Page<RehabilitationGroupDTO> page;
        if (eagerload) {
            page = rehabilitationGroupService.findAllWithEagerRelationships(pageable);
        } else {
            page = rehabilitationGroupService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/rehabilitation-groups?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rehabilitation-groups/:id : get the "id" rehabilitationGroup.
     *
     * @param id the id of the rehabilitationGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rehabilitationGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rehabilitation-groups/{id}")
    public ResponseEntity<RehabilitationGroupDTO> getRehabilitationGroup(@PathVariable Long id) {
        log.debug("REST request to get RehabilitationGroup : {}", id);
        Optional<RehabilitationGroupDTO> rehabilitationGroupDTO = rehabilitationGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rehabilitationGroupDTO);
    }

    /**
     * DELETE  /rehabilitation-groups/:id : delete the "id" rehabilitationGroup.
     *
     * @param id the id of the rehabilitationGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rehabilitation-groups/{id}")
    public ResponseEntity<Void> deleteRehabilitationGroup(@PathVariable Long id) {
        log.debug("REST request to delete RehabilitationGroup : {}", id);
        rehabilitationGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
