package com.dmmiralles.challenges.shop.service.impl;

import com.dmmiralles.challenges.shop.service.ShopService;
import com.dmmiralles.challenges.shop.domain.Shop;
import com.dmmiralles.challenges.shop.repository.ShopRepository;
import com.dmmiralles.challenges.shop.repository.search.ShopSearchRepository;
import com.dmmiralles.challenges.shop.service.dto.ShopDTO;
import com.dmmiralles.challenges.shop.service.mapper.ShopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Shop.
 */
@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    private final Logger log = LoggerFactory.getLogger(ShopServiceImpl.class);

    private final ShopRepository shopRepository;

    private final ShopMapper shopMapper;

    private final ShopSearchRepository shopSearchRepository;

    public ShopServiceImpl(ShopRepository shopRepository, ShopMapper shopMapper, ShopSearchRepository shopSearchRepository) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
        this.shopSearchRepository = shopSearchRepository;
    }

    /**
     * Save a shop.
     *
     * @param shopDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShopDTO save(ShopDTO shopDTO) {
        log.debug("Request to save Shop : {}", shopDTO);
        Shop shop = shopMapper.toEntity(shopDTO);
        shop = shopRepository.save(shop);
        ShopDTO result = shopMapper.toDto(shop);
        shopSearchRepository.save(shop);
        return result;
    }

    /**
     * Get all the shops.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShopDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Shops");
        return shopRepository.findAll(pageable)
            .map(shopMapper::toDto);
    }

    /**
     * Get one shop by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShopDTO findOne(Long id) {
        log.debug("Request to get Shop : {}", id);
        Shop shop = shopRepository.findOne(id);
        return shopMapper.toDto(shop);
    }

    /**
     * Delete the shop by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shop : {}", id);
        shopRepository.delete(id);
        shopSearchRepository.delete(id);
    }

    /**
     * Search for the shop corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShopDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Shops for query {}", query);
        Page<Shop> result = shopSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(shopMapper::toDto);
    }
}
