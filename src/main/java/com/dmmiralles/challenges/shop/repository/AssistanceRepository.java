package com.dmmiralles.challenges.shop.repository;

import com.dmmiralles.challenges.shop.domain.Assistance;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Assistance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssistanceRepository extends JpaRepository<Assistance, Long> {

}
