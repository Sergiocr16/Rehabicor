package com.aditum.rehabicor.service.mapper;

import com.aditum.rehabicor.domain.*;
import com.aditum.rehabicor.service.dto.DepressiveSymptomsSessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DepressiveSymptomsSession and its DTO DepressiveSymptomsSessionDTO.
 */
@Mapper(componentModel = "spring", uses = {SessionMapper.class})
public interface DepressiveSymptomsSessionMapper extends EntityMapper<DepressiveSymptomsSessionDTO, DepressiveSymptomsSession> {

    @Mapping(source = "session.id", target = "sessionId")
    DepressiveSymptomsSessionDTO toDto(DepressiveSymptomsSession depressiveSymptomsSession);

    @Mapping(source = "sessionId", target = "session")
    DepressiveSymptomsSession toEntity(DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO);

    default DepressiveSymptomsSession fromId(Long id) {
        if (id == null) {
            return null;
        }
        DepressiveSymptomsSession depressiveSymptomsSession = new DepressiveSymptomsSession();
        depressiveSymptomsSession.setId(id);
        return depressiveSymptomsSession;
    }
}
