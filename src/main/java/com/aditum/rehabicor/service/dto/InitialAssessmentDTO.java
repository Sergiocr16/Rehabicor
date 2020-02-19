package com.aditum.rehabicor.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InitialAssessment entity.
 */
public class InitialAssessmentDTO implements Serializable {

    private Long id;

    @NotNull
    private String smoking;

    @NotNull
    private String weight;

    @NotNull
    private String size;

    @NotNull
    private String iMC;

    private String hbiac;

    private String baselineFunctionalCapacity;

    private String lDL;

    private String hDL;

    private String cardiovascularRisk;

    private Boolean deleted;


    private Long patientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getiMC() {
        return iMC;
    }

    public void setiMC(String iMC) {
        this.iMC = iMC;
    }

    public String getHbiac() {
        return hbiac;
    }

    public void setHbiac(String hbiac) {
        this.hbiac = hbiac;
    }

    public String getBaselineFunctionalCapacity() {
        return baselineFunctionalCapacity;
    }

    public void setBaselineFunctionalCapacity(String baselineFunctionalCapacity) {
        this.baselineFunctionalCapacity = baselineFunctionalCapacity;
    }

    public String getlDL() {
        return lDL;
    }

    public void setlDL(String lDL) {
        this.lDL = lDL;
    }

    public String gethDL() {
        return hDL;
    }

    public void sethDL(String hDL) {
        this.hDL = hDL;
    }

    public String getCardiovascularRisk() {
        return cardiovascularRisk;
    }

    public void setCardiovascularRisk(String cardiovascularRisk) {
        this.cardiovascularRisk = cardiovascularRisk;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InitialAssessmentDTO initialAssessmentDTO = (InitialAssessmentDTO) o;
        if (initialAssessmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), initialAssessmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InitialAssessmentDTO{" +
            "id=" + getId() +
            ", smoking='" + getSmoking() + "'" +
            ", weight='" + getWeight() + "'" +
            ", size='" + getSize() + "'" +
            ", iMC='" + getiMC() + "'" +
            ", hbiac='" + getHbiac() + "'" +
            ", baselineFunctionalCapacity='" + getBaselineFunctionalCapacity() + "'" +
            ", lDL='" + getlDL() + "'" +
            ", hDL='" + gethDL() + "'" +
            ", cardiovascularRisk='" + getCardiovascularRisk() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", patient=" + getPatientId() +
            "}";
    }
}
