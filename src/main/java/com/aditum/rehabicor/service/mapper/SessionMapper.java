package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.SessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Session and its DTO SessionDTO.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface SessionMapper extends EntityMapper<SessionDTO, Session> {

    @Mapping(source = "patient.id", target = "patientId")
    SessionDTO toDto(Session session);

    @Mapping(target = "depressiveSymptomsSessions", ignore = true)
    @Mapping(target = "mayorEventsSessions", ignore = true)
    @Mapping(target = "minorEventsSessions", ignore = true)
    @Mapping(target = "nonSpecificPainsSessions", ignore = true)
    @Mapping(source = "patientId", target = "patient")
    Session toEntity(SessionDTO sessionDTO);

    default Session fromId(Long id) {
        if (id == null) {
            return null;
        }
        Session session = new Session();
        session.setId(id);
        return session;
    }
}
