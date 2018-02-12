package com.dmmiralles.challenges.shop.service;

import com.dmmiralles.challenges.shop.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Employee.
 */
public interface EmployeeService {

    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save
     * @return the persisted entity
     */
    EmployeeDTO save(EmployeeDTO employeeDTO);

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmployeeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" employee.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EmployeeDTO findOne(Long id);

    /**
     * Delete the "id" employee.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the employee corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmployeeDTO> search(String query, Pageable pageable);
}
