package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.NonSpecificPainDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity NonSpecificPain and its DTO NonSpecificPainDTO.
 */
@Mapper(componentModel = "spring", uses = {RehabilitationCenterMapper.class})
public interface NonSpecificPainMapper extends EntityMapper<NonSpecificPainDTO, NonSpecificPain> {

    @Mapping(source = "rehabilitationCenter.id", target = "rehabilitationCenterId")
    NonSpecificPainDTO toDto(NonSpecificPain nonSpecificPain);

    @Mapping(source = "rehabilitationCenterId", target = "rehabilitationCenter")
    NonSpecificPain toEntity(NonSpecificPainDTO nonSpecificPainDTO);

    default NonSpecificPain fromId(Long id) {
        if (id == null) {
            return null;
        }
        NonSpecificPain nonSpecificPain = new NonSpecificPain();
        nonSpecificPain.setId(id);
        return nonSpecificPain;
    }
}
