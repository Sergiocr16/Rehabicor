package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.ComorbiditieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comorbiditie and its DTO ComorbiditieDTO.
 */
@Mapper(componentModel = "spring", uses = {RehabilitationCenterMapper.class})
public interface ComorbiditieMapper extends EntityMapper<ComorbiditieDTO, Comorbiditie> {

    @Mapping(source = "rehabilitationCenter.id", target = "rehabilitationCenterId")
    ComorbiditieDTO toDto(Comorbiditie comorbiditie);

    @Mapping(source = "rehabilitationCenterId", target = "rehabilitationCenter")
    Comorbiditie toEntity(ComorbiditieDTO comorbiditieDTO);

    default Comorbiditie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comorbiditie comorbiditie = new Comorbiditie();
        comorbiditie.setId(id);
        return comorbiditie;
    }
}
