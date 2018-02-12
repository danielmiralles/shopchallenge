package com.dmmiralles.challenges.shop.service;

import com.dmmiralles.challenges.shop.service.dto.EmployeeTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EmployeeType.
 */
public interface EmployeeTypeService {

    /**
     * Save a employeeType.
     *
     * @param employeeTypeDTO the entity to save
     * @return the persisted entity
     */
    EmployeeTypeDTO save(EmployeeTypeDTO employeeTypeDTO);

    /**
     * Get all the employeeTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmployeeTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" employeeType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EmployeeTypeDTO findOne(Long id);

    /**
     * Delete the "id" employeeType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the employeeType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmployeeTypeDTO> search(String query, Pageable pageable);
}
