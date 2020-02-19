package com.aditum.rehabicor.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MinorEventsSession entity.
 */
public class MinorEventsSessionDTO implements Serializable {

    private Long id;

    private String description;

    @NotNull
    private Boolean exist;

    private Long minorEventRelation;


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

    public Long getMinorEventRelation() {
        return minorEventRelation;
    }

    public void setMinorEventRelation(Long minorEventRelation) {
        this.minorEventRelation = minorEventRelation;
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

        MinorEventsSessionDTO minorEventsSessionDTO = (MinorEventsSessionDTO) o;
        if (minorEventsSessionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), minorEventsSessionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MinorEventsSessionDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", exist='" + isExist() + "'" +
            ", minorEventRelation=" + getMinorEventRelation() +
            ", session=" + getSessionId() +
            "}";
    }
}
