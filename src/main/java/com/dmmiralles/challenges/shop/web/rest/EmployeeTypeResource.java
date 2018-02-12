package com.dmmiralles.challenges.shop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dmmiralles.challenges.shop.service.EmployeeTypeService;
import com.dmmiralles.challenges.shop.web.rest.errors.BadRequestAlertException;
import com.dmmiralles.challenges.shop.web.rest.util.HeaderUtil;
import com.dmmiralles.challenges.shop.web.rest.util.PaginationUtil;
import com.dmmiralles.challenges.shop.service.dto.EmployeeTypeDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing EmployeeType.
 */
@RestController
@RequestMapping("/api")
public class EmployeeTypeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeTypeResource.class);

    private static final String ENTITY_NAME = "employeeType";

    private final EmployeeTypeService employeeTypeService;

    public EmployeeTypeResource(EmployeeTypeService employeeTypeService) {
        this.employeeTypeService = employeeTypeService;
    }

    /**
     * POST  /employee-types : Create a new employeeType.
     *
     * @param employeeTypeDTO the employeeTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeTypeDTO, or with status 400 (Bad Request) if the employeeType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-types")
    @Timed
    public ResponseEntity<EmployeeTypeDTO> createEmployeeType(@Valid @RequestBody EmployeeTypeDTO employeeTypeDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeType : {}", employeeTypeDTO);
        if (employeeTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeTypeDTO result = employeeTypeService.save(employeeTypeDTO);
        return ResponseEntity.created(new URI("/api/employee-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-types : Updates an existing employeeType.
     *
     * @param employeeTypeDTO the employeeTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeTypeDTO,
     * or with status 400 (Bad Request) if the employeeTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the employeeTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-types")
    @Timed
    public ResponseEntity<EmployeeTypeDTO> updateEmployeeType(@Valid @RequestBody EmployeeTypeDTO employeeTypeDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeType : {}", employeeTypeDTO);
        if (employeeTypeDTO.getId() == null) {
            return createEmployeeType(employeeTypeDTO);
        }
        EmployeeTypeDTO result = employeeTypeService.save(employeeTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-types : get all the employeeTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of employeeTypes in body
     */
    @GetMapping("/employee-types")
    @Timed
    public ResponseEntity<List<EmployeeTypeDTO>> getAllEmployeeTypes(Pageable pageable) {
        log.debug("REST request to get a page of EmployeeTypes");
        Page<EmployeeTypeDTO> page = employeeTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employee-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employee-types/:id : get the "id" employeeType.
     *
     * @param id the id of the employeeTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/employee-types/{id}")
    @Timed
    public ResponseEntity<EmployeeTypeDTO> getEmployeeType(@PathVariable Long id) {
        log.debug("REST request to get EmployeeType : {}", id);
        EmployeeTypeDTO employeeTypeDTO = employeeTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeTypeDTO));
    }

    /**
     * DELETE  /employee-types/:id : delete the "id" employeeType.
     *
     * @param id the id of the employeeTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeType(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeType : {}", id);
        employeeTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/employee-types?query=:query : search for the employeeType corresponding
     * to the query.
     *
     * @param query the query of the employeeType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/employee-types")
    @Timed
    public ResponseEntity<List<EmployeeTypeDTO>> searchEmployeeTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EmployeeTypes for query {}", query);
        Page<EmployeeTypeDTO> page = employeeTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/employee-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
