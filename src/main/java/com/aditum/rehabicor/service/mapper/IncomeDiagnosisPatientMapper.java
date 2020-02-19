package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.IncomeDiagnosisPatientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IncomeDiagnosisPatient and its DTO IncomeDiagnosisPatientDTO.
 */
@Mapper(componentModel = "spring", uses = {InitialAssessmentMapper.class})
public interface IncomeDiagnosisPatientMapper extends EntityMapper<IncomeDiagnosisPatientDTO, IncomeDiagnosisPatient> {

    @Mapping(source = "initialAssessment.id", target = "initialAssessmentId")
    IncomeDiagnosisPatientDTO toDto(IncomeDiagnosisPatient incomeDiagnosisPatient);

    @Mapping(source = "initialAssessmentId", target = "initialAssessment")
    IncomeDiagnosisPatient toEntity(IncomeDiagnosisPatientDTO incomeDiagnosisPatientDTO);

    default IncomeDiagnosisPatient fromId(Long id) {
        if (id == null) {
            return null;
        }
        IncomeDiagnosisPatient incomeDiagnosisPatient = new IncomeDiagnosisPatient();
        incomeDiagnosisPatient.setId(id);
        return incomeDiagnosisPatient;
    }
}
