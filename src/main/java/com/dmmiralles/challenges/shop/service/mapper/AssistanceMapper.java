package com.dmmiralles.challenges.shop.service.mapper;

import com.dmmiralles.challenges.shop.domain.*;
import com.dmmiralles.challenges.shop.service.dto.AssistanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Assistance and its DTO AssistanceDTO.
 */
@Mapper(componentModel = "spring", uses = {ShopMapper.class, EmployeeMapper.class})
public interface AssistanceMapper extends EntityMapper<AssistanceDTO, Assistance> {

    @Mapping(source = "shop.id", target = "shopId")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "shop.name", target = "shopName")
    @Mapping(source = "employee.name", target = "employeeName")
    AssistanceDTO toDto(Assistance assistance);

    @Mapping(source = "shopId", target = "shop")
    @Mapping(source = "employeeId", target = "employee")
    Assistance toEntity(AssistanceDTO assistanceDTO);

    default Assistance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Assistance assistance = new Assistance();
        assistance.setId(id);
        return assistance;
    }
}
