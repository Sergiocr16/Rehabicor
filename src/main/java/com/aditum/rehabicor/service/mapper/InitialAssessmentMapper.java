package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.InitialAssessmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InitialAssessment and its DTO InitialAssessmentDTO.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface InitialAssessmentMapper extends EntityMapper<InitialAssessmentDTO, InitialAssessment> {

    @Mapping(source = "patient.id", target = "patientId")
    InitialAssessmentDTO toDto(InitialAssessment initialAssessment);

    @Mapping(target = "comorbiditiesPatients", ignore = true)
    @Mapping(target = "incomeDiagnosisPatients", ignore = true)
    @Mapping(source = "patientId", target = "patient")
    InitialAssessment toEntity(InitialAssessmentDTO initialAssessmentDTO);

    default InitialAssessment fromId(Long id) {
        if (id == null) {
            return null;
        }
        InitialAssessment initialAssessment = new InitialAssessment();
        initialAssessment.setId(id);
        return initialAssessment;
    }
}
