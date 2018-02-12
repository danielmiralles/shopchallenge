package com.dmmiralles.challenges.shop.repository;

import com.dmmiralles.challenges.shop.domain.Shop;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Shop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

}
