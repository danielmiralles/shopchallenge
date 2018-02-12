package com.dmmiralles.challenges.shop.service.impl;

import com.dmmiralles.challenges.shop.service.EmployeeTypeService;
import com.dmmiralles.challenges.shop.domain.EmployeeType;
import com.dmmiralles.challenges.shop.repository.EmployeeTypeRepository;
import com.dmmiralles.challenges.shop.repository.search.EmployeeTypeSearchRepository;
import com.dmmiralles.challenges.shop.service.dto.EmployeeTypeDTO;
import com.dmmiralles.challenges.shop.service.mapper.EmployeeTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EmployeeType.
 */
@Service
@Transactional
public class EmployeeTypeServiceImpl implements EmployeeTypeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeTypeServiceImpl.class);

    private final EmployeeTypeRepository employeeTypeRepository;

    private final EmployeeTypeMapper employeeTypeMapper;

    private final EmployeeTypeSearchRepository employeeTypeSearchRepository;

    public EmployeeTypeServiceImpl(EmployeeTypeRepository employeeTypeRepository, EmployeeTypeMapper employeeTypeMapper, EmployeeTypeSearchRepository employeeTypeSearchRepository) {
        this.employeeTypeRepository = employeeTypeRepository;
        this.employeeTypeMapper = employeeTypeMapper;
        this.employeeTypeSearchRepository = employeeTypeSearchRepository;
    }

    /**
     * Save a employeeType.
     *
     * @param employeeTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmployeeTypeDTO save(EmployeeTypeDTO employeeTypeDTO) {
        log.debug("Request to save EmployeeType : {}", employeeTypeDTO);
        EmployeeType employeeType = employeeTypeMapper.toEntity(employeeTypeDTO);
        employeeType = employeeTypeRepository.save(employeeType);
        EmployeeTypeDTO result = employeeTypeMapper.toDto(employeeType);
        employeeTypeSearchRepository.save(employeeType);
        return result;
    }

    /**
     * Get all the employeeTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeTypes");
        return employeeTypeRepository.findAll(pageable)
            .map(employeeTypeMapper::toDto);
    }

    /**
     * Get one employeeType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmployeeTypeDTO findOne(Long id) {
        log.debug("Request to get EmployeeType : {}", id);
        EmployeeType employeeType = employeeTypeRepository.findOne(id);
        return employeeTypeMapper.toDto(employeeType);
    }

    /**
     * Delete the employeeType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeType : {}", id);
        employeeTypeRepository.delete(id);
        employeeTypeSearchRepository.delete(id);
    }

    /**
     * Search for the employeeType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EmployeeTypes for query {}", query);
        Page<EmployeeType> result = employeeTypeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(employeeTypeMapper::toDto);
    }
}
