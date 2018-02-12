package com.dmmiralles.challenges.shop.service.mapper;

import com.dmmiralles.challenges.shop.domain.*;
import com.dmmiralles.challenges.shop.service.dto.EmployeeTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EmployeeType and its DTO EmployeeTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeTypeMapper extends EntityMapper<EmployeeTypeDTO, EmployeeType> {



    default EmployeeType fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeType employeeType = new EmployeeType();
        employeeType.setId(id);
        return employeeType;
    }
}
