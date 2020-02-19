package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.NonSpecificPainsSessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity NonSpecificPainsSession and its DTO NonSpecificPainsSessionDTO.
 */
@Mapper(componentModel = "spring", uses = {SessionMapper.class})
public interface NonSpecificPainsSessionMapper extends EntityMapper<NonSpecificPainsSessionDTO, NonSpecificPainsSession> {

    @Mapping(source = "session.id", target = "sessionId")
    NonSpecificPainsSessionDTO toDto(NonSpecificPainsSession nonSpecificPainsSession);

    @Mapping(source = "sessionId", target = "session")
    NonSpecificPainsSession toEntity(NonSpecificPainsSessionDTO nonSpecificPainsSessionDTO);

    default NonSpecificPainsSession fromId(Long id) {
        if (id == null) {
            return null;
        }
        NonSpecificPainsSession nonSpecificPainsSession = new NonSpecificPainsSession();
        nonSpecificPainsSession.setId(id);
        return nonSpecificPainsSession;
    }
}
