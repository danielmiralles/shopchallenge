package com.dmmiralles.challenges.shop.service;

import com.dmmiralles.challenges.shop.service.dto.AssistanceDTO;
import java.util.List;

/**
 * Service Interface for managing Assistance.
 */
public interface AssistanceService {

    /**
     * Save a assistance.
     *
     * @param assistanceDTO the entity to save
     * @return the persisted entity
     */
    AssistanceDTO save(AssistanceDTO assistanceDTO);

    /**
     * Get all the assistances.
     *
     * @return the list of entities
     */
    List<AssistanceDTO> findAll();

    /**
     * Get the "id" assistance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AssistanceDTO findOne(Long id);

    /**
     * Delete the "id" assistance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the assistance corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<AssistanceDTO> search(String query);
}
