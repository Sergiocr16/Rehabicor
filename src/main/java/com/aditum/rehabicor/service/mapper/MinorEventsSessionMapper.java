package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.MinorEventsSessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MinorEventsSession and its DTO MinorEventsSessionDTO.
 */
@Mapper(componentModel = "spring", uses = {SessionMapper.class})
public interface MinorEventsSessionMapper extends EntityMapper<MinorEventsSessionDTO, MinorEventsSession> {

    @Mapping(source = "session.id", target = "sessionId")
    MinorEventsSessionDTO toDto(MinorEventsSession minorEventsSession);

    @Mapping(source = "sessionId", target = "session")
    MinorEventsSession toEntity(MinorEventsSessionDTO minorEventsSessionDTO);

    default MinorEventsSession fromId(Long id) {
        if (id == null) {
            return null;
        }
        MinorEventsSession minorEventsSession = new MinorEventsSession();
        minorEventsSession.setId(id);
        return minorEventsSession;
    }
}
