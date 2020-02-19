package com.aditum.rehabicor.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the IncomeDiagnosisPatient entity.
 */
public class IncomeDiagnosisPatientDTO implements Serializable {

    private Long id;

    private String description;

    @NotNull
    private Boolean exist;

    private Long incomeDiagnosisRelation;


    private Long initialAssessmentId;

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

    public Long getIncomeDiagnosisRelation() {
        return incomeDiagnosisRelation;
    }

    public void setIncomeDiagnosisRelation(Long incomeDiagnosisRelation) {
        this.incomeDiagnosisRelation = incomeDiagnosisRelation;
    }

    public Long getInitialAssessmentId() {
        return initialAssessmentId;
    }

    public void setInitialAssessmentId(Long initialAssessmentId) {
        this.initialAssessmentId = initialAssessmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncomeDiagnosisPatientDTO incomeDiagnosisPatientDTO = (IncomeDiagnosisPatientDTO) o;
        if (incomeDiagnosisPatientDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incomeDiagnosisPatientDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncomeDiagnosisPatientDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", exist='" + isExist() + "'" +
            ", incomeDiagnosisRelation=" + getIncomeDiagnosisRelation() +
            ", initialAssessment=" + getInitialAssessmentId() +
            "}";
    }
}
