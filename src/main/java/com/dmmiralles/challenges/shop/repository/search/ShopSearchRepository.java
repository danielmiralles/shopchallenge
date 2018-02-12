package com.dmmiralles.challenges.shop.repository.search;

import com.dmmiralles.challenges.shop.domain.Shop;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Shop entity.
 */
public interface ShopSearchRepository extends ElasticsearchRepository<Shop, Long> {
}
