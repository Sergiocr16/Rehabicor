package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.IncomeDiagnosisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IncomeDiagnosis and its DTO IncomeDiagnosisDTO.
 */
@Mapper(componentModel = "spring", uses = {RehabilitationCenterMapper.class})
public interface IncomeDiagnosisMapper extends EntityMapper<IncomeDiagnosisDTO, IncomeDiagnosis> {

    @Mapping(source = "rehabilitationCenter.id", target = "rehabilitationCenterId")
    IncomeDiagnosisDTO toDto(IncomeDiagnosis incomeDiagnosis);

    @Mapping(source = "rehabilitationCenterId", target = "rehabilitationCenter")
    IncomeDiagnosis toEntity(IncomeDiagnosisDTO incomeDiagnosisDTO);

    default IncomeDiagnosis fromId(Long id) {
        if (id == null) {
            return null;
        }
        IncomeDiagnosis incomeDiagnosis = new IncomeDiagnosis();
        incomeDiagnosis.setId(id);
        return incomeDiagnosis;
    }
}
