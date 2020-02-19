package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.ComorbiditiesPatientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ComorbiditiesPatient and its DTO ComorbiditiesPatientDTO.
 */
@Mapper(componentModel = "spring", uses = {InitialAssessmentMapper.class})
public interface ComorbiditiesPatientMapper extends EntityMapper<ComorbiditiesPatientDTO, ComorbiditiesPatient> {

    @Mapping(source = "initialAssessment.id", target = "initialAssessmentId")
    ComorbiditiesPatientDTO toDto(ComorbiditiesPatient comorbiditiesPatient);

    @Mapping(source = "initialAssessmentId", target = "initialAssessment")
    ComorbiditiesPatient toEntity(ComorbiditiesPatientDTO comorbiditiesPatientDTO);

    default ComorbiditiesPatient fromId(Long id) {
        if (id == null) {
            return null;
        }
        ComorbiditiesPatient comorbiditiesPatient = new ComorbiditiesPatient();
        comorbiditiesPatient.setId(id);
        return comorbiditiesPatient;
    }
}
