package com.dmmiralles.challenges.shop.service.mapper;

import com.dmmiralles.challenges.shop.domain.*;
import com.dmmiralles.challenges.shop.service.dto.ShopDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Shop and its DTO ShopDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShopMapper extends EntityMapper<ShopDTO, Shop> {



    default Shop fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shop shop = new Shop();
        shop.setId(id);
        return shop;
    }
}
