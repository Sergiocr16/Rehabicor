package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.DepressiveSymptomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DepressiveSymptom and its DTO DepressiveSymptomDTO.
 */
@Mapper(componentModel = "spring", uses = {RehabilitationCenterMapper.class})
public interface DepressiveSymptomMapper extends EntityMapper<DepressiveSymptomDTO, DepressiveSymptom> {

    @Mapping(source = "rehabilitationCenter.id", target = "rehabilitationCenterId")
    DepressiveSymptomDTO toDto(DepressiveSymptom depressiveSymptom);

    @Mapping(source = "rehabilitationCenterId", target = "rehabilitationCenter")
    DepressiveSymptom toEntity(DepressiveSymptomDTO depressiveSymptomDTO);

    default DepressiveSymptom fromId(Long id) {
        if (id == null) {
            return null;
        }
        DepressiveSymptom depressiveSymptom = new DepressiveSymptom();
        depressiveSymptom.setId(id);
        return depressiveSymptom;
    }
}
