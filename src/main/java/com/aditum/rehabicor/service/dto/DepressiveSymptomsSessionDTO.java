package com.aditum.rehabicor.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DepressiveSymptomsSession entity.
 */
public class DepressiveSymptomsSessionDTO implements Serializable {

    private Long id;

    private String description;

    @NotNull
    private Boolean exist;

    private Long depressiveSymptomRelation;


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

    public Long getDepressiveSymptomRelation() {
        return depressiveSymptomRelation;
    }

    public void setDepressiveSymptomRelation(Long depressiveSymptomRelation) {
        this.depressiveSymptomRelation = depressiveSymptomRelation;
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

        DepressiveSymptomsSessionDTO depressiveSymptomsSessionDTO = (DepressiveSymptomsSessionDTO) o;
        if (depressiveSymptomsSessionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depressiveSymptomsSessionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepressiveSymptomsSessionDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", exist='" + isExist() + "'" +
            ", depressiveSymptomRelation=" + getDepressiveSymptomRelation() +
            ", session=" + getSessionId() +
            "}";
    }
}
