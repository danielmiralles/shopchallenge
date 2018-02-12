package com.dmmiralles.challenges.shop.repository;

import com.dmmiralles.challenges.shop.domain.EmployeeType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, Long> {

}
