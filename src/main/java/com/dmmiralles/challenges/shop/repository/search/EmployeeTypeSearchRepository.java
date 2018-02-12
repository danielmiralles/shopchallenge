package com.dmmiralles.challenges.shop.repository.search;

import com.dmmiralles.challenges.shop.domain.EmployeeType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EmployeeType entity.
 */
public interface EmployeeTypeSearchRepository extends ElasticsearchRepository<EmployeeType, Long> {
}
