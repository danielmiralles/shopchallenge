package com.dmmiralles.challenges.shop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dmmiralles.challenges.shop.service.AssistanceService;
import com.dmmiralles.challenges.shop.web.rest.errors.BadRequestAlertException;
import com.dmmiralles.challenges.shop.web.rest.util.HeaderUtil;
import com.dmmiralles.challenges.shop.service.dto.AssistanceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Assistance.
 */
@RestController
@RequestMapping("/api")
public class AssistanceResource {

    private final Logger log = LoggerFactory.getLogger(AssistanceResource.class);

    private static final String ENTITY_NAME = "assistance";

    private final AssistanceService assistanceService;

    public AssistanceResource(AssistanceService assistanceService) {
        this.assistanceService = assistanceService;
    }

    /**
     * POST  /assistances : Create a new assistance.
     *
     * @param assistanceDTO the assistanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assistanceDTO, or with status 400 (Bad Request) if the assistance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assistances")
    @Timed
    public ResponseEntity<AssistanceDTO> createAssistance(@Valid @RequestBody AssistanceDTO assistanceDTO) throws URISyntaxException {
        log.debug("REST request to save Assistance : {}", assistanceDTO);
        if (assistanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new assistance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssistanceDTO result = assistanceService.save(assistanceDTO);
        return ResponseEntity.created(new URI("/api/assistances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assistances : Updates an existing assistance.
     *
     * @param assistanceDTO the assistanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assistanceDTO,
     * or with status 400 (Bad Request) if the assistanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the assistanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assistances")
    @Timed
    public ResponseEntity<AssistanceDTO> updateAssistance(@Valid @RequestBody AssistanceDTO assistanceDTO) throws URISyntaxException {
        log.debug("REST request to update Assistance : {}", assistanceDTO);
        if (assistanceDTO.getId() == null) {
            return createAssistance(assistanceDTO);
        }
        AssistanceDTO result = assistanceService.save(assistanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assistanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assistances : get all the assistances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assistances in body
     */
    @GetMapping("/assistances")
    @Timed
    public List<AssistanceDTO> getAllAssistances() {
        log.debug("REST request to get all Assistances");
        return assistanceService.findAll();
        }

    /**
     * GET  /assistances/:id : get the "id" assistance.
     *
     * @param id the id of the assistanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assistanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/assistances/{id}")
    @Timed
    public ResponseEntity<AssistanceDTO> getAssistance(@PathVariable Long id) {
        log.debug("REST request to get Assistance : {}", id);
        AssistanceDTO assistanceDTO = assistanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assistanceDTO));
    }

    /**
     * DELETE  /assistances/:id : delete the "id" assistance.
     *
     * @param id the id of the assistanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assistances/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssistance(@PathVariable Long id) {
        log.debug("REST request to delete Assistance : {}", id);
        assistanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/assistances?query=:query : search for the assistance corresponding
     * to the query.
     *
     * @param query the query of the assistance search
     * @return the result of the search
     */
    @GetMapping("/_search/assistances")
    @Timed
    public List<AssistanceDTO> searchAssistances(@RequestParam String query) {
        log.debug("REST request to search Assistances for query {}", query);
        return assistanceService.search(query);
    }

}
