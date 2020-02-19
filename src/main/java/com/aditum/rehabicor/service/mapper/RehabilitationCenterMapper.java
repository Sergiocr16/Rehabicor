package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.RehabilitationCenterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RehabilitationCenter and its DTO RehabilitationCenterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RehabilitationCenterMapper extends EntityMapper<RehabilitationCenterDTO, RehabilitationCenter> {


    @Mapping(target = "appUsers", ignore = true)
    @Mapping(target = "comorbidities", ignore = true)
    @Mapping(target = "depressiveSymptoms", ignore = true)
    @Mapping(target = "incomeDiagnoses", ignore = true)
    @Mapping(target = "mayorEvents", ignore = true)
    @Mapping(target = "minorEvents", ignore = true)
    @Mapping(target = "nonSpecificPains", ignore = true)
    @Mapping(target = "rehabilitationGroups", ignore = true)
    RehabilitationCenter toEntity(RehabilitationCenterDTO rehabilitationCenterDTO);

    default RehabilitationCenter fromId(Long id) {
        if (id == null) {
            return null;
        }
        RehabilitationCenter rehabilitationCenter = new RehabilitationCenter();
        rehabilitationCenter.setId(id);
        return rehabilitationCenter;
    }
}
