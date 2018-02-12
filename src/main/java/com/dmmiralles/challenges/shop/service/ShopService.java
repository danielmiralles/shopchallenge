package com.dmmiralles.challenges.shop.service;

import com.dmmiralles.challenges.shop.service.dto.ShopDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Shop.
 */
public interface ShopService {

    /**
     * Save a shop.
     *
     * @param shopDTO the entity to save
     * @return the persisted entity
     */
    ShopDTO save(ShopDTO shopDTO);

    /**
     * Get all the shops.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShopDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shop.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ShopDTO findOne(Long id);

    /**
     * Delete the "id" shop.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shop corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShopDTO> search(String query, Pageable pageable);
}
