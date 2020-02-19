package com.aditum.rehabicor.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the NonSpecificPainsSession entity.
 */
public class NonSpecificPainsSessionDTO implements Serializable {

    private Long id;

    private String description;

    @NotNull
    private Boolean exist;

    private Long nonSpecificPainsSessionRelation;


    private Long sessionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    public Long getNonSpecificPainsSessionRelation() {
        return nonSpecificPainsSessionRelation;
    }

    public void setNonSpecificPainsSessionRelation(Long nonSpecificPainsSessionRelation) {
        this.nonSpecificPainsSessionRelation = nonSpecificPainsSessionRelation;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NonSpecificPainsSessionDTO nonSpecificPainsSessionDTO = (NonSpecificPainsSessionDTO) o;
        if (nonSpecificPainsSessionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nonSpecificPainsSessionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NonSpecificPainsSessionDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", exist='" + isExist() + "'" +
            ", nonSpecificPainsSessionRelation=" + getNonSpecificPainsSessionRelation() +
            ", session=" + getSessionId() +
            "}";
    }
}
