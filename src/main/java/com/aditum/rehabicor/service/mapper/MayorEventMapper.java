package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.MayorEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MayorEvent and its DTO MayorEventDTO.
 */
@Mapper(componentModel = "spring", uses = {RehabilitationCenterMapper.class})
public interface MayorEventMapper extends EntityMapper<MayorEventDTO, MayorEvent> {

    @Mapping(source = "rehabilitationCenter.id", target = "rehabilitationCenterId")
    MayorEventDTO toDto(MayorEvent mayorEvent);

    @Mapping(source = "rehabilitationCenterId", target = "rehabilitationCenter")
    MayorEvent toEntity(MayorEventDTO mayorEventDTO);

    default MayorEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        MayorEvent mayorEvent = new MayorEvent();
        mayorEvent.setId(id);
        return mayorEvent;
    }
}
