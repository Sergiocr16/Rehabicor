package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.RehabilitationGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RehabilitationGroup and its DTO RehabilitationGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class, RehabilitationCenterMapper.class})
public interface RehabilitationGroupMapper extends EntityMapper<RehabilitationGroupDTO, RehabilitationGroup> {

    @Mapping(source = "rehabilitationCenter.id", target = "rehabilitationCenterId")
    RehabilitationGroupDTO toDto(RehabilitationGroup rehabilitationGroup);

    @Mapping(source = "rehabilitationCenterId", target = "rehabilitationCenter")
    RehabilitationGroup toEntity(RehabilitationGroupDTO rehabilitationGroupDTO);

    default RehabilitationGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        RehabilitationGroup rehabilitationGroup = new RehabilitationGroup();
        rehabilitationGroup.setId(id);
        return rehabilitationGroup;
    }
}
