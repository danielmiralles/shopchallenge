package com.dmmiralles.challenges.shop.repository.search;

import com.dmmiralles.challenges.shop.domain.Assistance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Assistance entity.
 */
public interface AssistanceSearchRepository extends ElasticsearchRepository<Assistance, Long> {
}
