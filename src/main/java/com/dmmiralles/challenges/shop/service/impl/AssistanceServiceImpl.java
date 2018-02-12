package com.dmmiralles.challenges.shop.service.impl;

import com.dmmiralles.challenges.shop.service.AssistanceService;
import com.dmmiralles.challenges.shop.domain.Assistance;
import com.dmmiralles.challenges.shop.repository.AssistanceRepository;
import com.dmmiralles.challenges.shop.repository.search.AssistanceSearchRepository;
import com.dmmiralles.challenges.shop.service.dto.AssistanceDTO;
import com.dmmiralles.challenges.shop.service.mapper.AssistanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Assistance.
 */
@Service
@Transactional
public class AssistanceServiceImpl implements AssistanceService {

    private final Logger log = LoggerFactory.getLogger(AssistanceServiceImpl.class);

    private final AssistanceRepository assistanceRepository;

    private final AssistanceMapper assistanceMapper;

    private final AssistanceSearchRepository assistanceSearchRepository;

    public AssistanceServiceImpl(AssistanceRepository assistanceRepository, AssistanceMapper assistanceMapper, AssistanceSearchRepository assistanceSearchRepository) {
        this.assistanceRepository = assistanceRepository;
        this.assistanceMapper = assistanceMapper;
        this.assistanceSearchRepository = assistanceSearchRepository;
    }

    /**
     * Save a assistance.
     *
     * @param assistanceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AssistanceDTO save(AssistanceDTO assistanceDTO) {
        log.debug("Request to save Assistance : {}", assistanceDTO);
        Assistance assistance = assistanceMapper.toEntity(assistanceDTO);
        assistance = assistanceRepository.save(assistance);
        AssistanceDTO result = assistanceMapper.toDto(assistance);
        assistanceSearchRepository.save(assistance);
        return result;
    }

    /**
     * Get all the assistances.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AssistanceDTO> findAll() {
        log.debug("Request to get all Assistances");
        return assistanceRepository.findAll().stream()
            .map(assistanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one assistance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AssistanceDTO findOne(Long id) {
        log.debug("Request to get Assistance : {}", id);
        Assistance assistance = assistanceRepository.findOne(id);
        return assistanceMapper.toDto(assistance);
    }

    /**
     * Delete the assistance by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assistance : {}", id);
        assistanceRepository.delete(id);
        assistanceSearchRepository.delete(id);
    }

    /**
     * Search for the assistance corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AssistanceDTO> search(String query) {
        log.debug("Request to search Assistances for query {}", query);
        return StreamSupport
            .stream(assistanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(assistanceMapper::toDto)
            .collect(Collectors.toList());
    }
}
