package com.aditum.rehabicor.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FinalAssessment entity.
 */
public class FinalAssessmentDTO implements Serializable {

    private Long id;

    private String smoking;

    private String weight;

    private String size;

    private String iMC;

    private String hbiac;

    private String baselineFunctionalCapacity;

    private String lDL;

    private String hDL;

    private String cardiovascularRisk;

    private Boolean isWorking;

    private Boolean deceased;

    private Boolean abandonment;

    private Boolean abandonmentMedicCause;

    private Boolean hospitalized;

    private Boolean deleted;

    private Boolean reevaluation;

    private ZonedDateTime executionDate;


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

    public Boolean isIsWorking() {
        return isWorking;
    }

    public void setIsWorking(Boolean isWorking) {
        this.isWorking = isWorking;
    }

    public Boolean isDeceased() {
        return deceased;
    }

    public void setDeceased(Boolean deceased) {
        this.deceased = deceased;
    }

    public Boolean isAbandonment() {
        return abandonment;
    }

    public void setAbandonment(Boolean abandonment) {
        this.abandonment = abandonment;
    }

    public Boolean isAbandonmentMedicCause() {
        return abandonmentMedicCause;
    }

    public void setAbandonmentMedicCause(Boolean abandonmentMedicCause) {
        this.abandonmentMedicCause = abandonmentMedicCause;
    }

    public Boolean isHospitalized() {
        return hospitalized;
    }

    public void setHospitalized(Boolean hospitalized) {
        this.hospitalized = hospitalized;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean isReevaluation() {
        return reevaluation;
    }

    public void setReevaluation(Boolean reevaluation) {
        this.reevaluation = reevaluation;
    }

    public ZonedDateTime getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(ZonedDateTime executionDate) {
        this.executionDate = executionDate;
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

        FinalAssessmentDTO finalAssessmentDTO = (FinalAssessmentDTO) o;
        if (finalAssessmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), finalAssessmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinalAssessmentDTO{" +
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
            ", isWorking='" + isIsWorking() + "'" +
            ", deceased='" + isDeceased() + "'" +
            ", abandonment='" + isAbandonment() + "'" +
            ", abandonmentMedicCause='" + isAbandonmentMedicCause() + "'" +
            ", hospitalized='" + isHospitalized() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", reevaluation='" + isReevaluation() + "'" +
            ", executionDate='" + getExecutionDate() + "'" +
            ", patient=" + getPatientId() +
            "}";
    }
}
