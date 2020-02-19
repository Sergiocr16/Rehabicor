package com.aditum.rehabicor.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A FinalAssessment.
 */
@Entity
@Table(name = "final_assessment")
public class FinalAssessment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "smoking")
    private String smoking;

    @Column(name = "weight")
    private String weight;

    @Column(name = "jhi_size")
    private String size;

    @Column(name = "i_mc")
    private String iMC;

    @Column(name = "hbiac")
    private String hbiac;

    @Column(name = "baseline_functional_capacity")
    private String baselineFunctionalCapacity;

    @Column(name = "l_dl")
    private String lDL;

    @Column(name = "h_dl")
    private String hDL;

    @Column(name = "cardiovascular_risk")
    private String cardiovascularRisk;

    @Column(name = "is_working")
    private Boolean isWorking;

    @Column(name = "deceased")
    private Boolean deceased;

    @Column(name = "abandonment")
    private Boolean abandonment;

    @Column(name = "abandonment_medic_cause")
    private Boolean abandonmentMedicCause;

    @Column(name = "hospitalized")
    private Boolean hospitalized;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "reevaluation")
    private Boolean reevaluation;

    @Column(name = "execution_date")
    private ZonedDateTime executionDate;

    @ManyToOne
    @JsonIgnoreProperties("finalAssessments")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmoking() {
        return smoking;
    }

    public FinalAssessment smoking(String smoking) {
        this.smoking = smoking;
        return this;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getWeight() {
        return weight;
    }

    public FinalAssessment weight(String weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public FinalAssessment size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getiMC() {
        return iMC;
    }

    public FinalAssessment iMC(String iMC) {
        this.iMC = iMC;
        return this;
    }

    public void setiMC(String iMC) {
        this.iMC = iMC;
    }

    public String getHbiac() {
        return hbiac;
    }

    public FinalAssessment hbiac(String hbiac) {
        this.hbiac = hbiac;
        return this;
    }

    public void setHbiac(String hbiac) {
        this.hbiac = hbiac;
    }

    public String getBaselineFunctionalCapacity() {
        return baselineFunctionalCapacity;
    }

    public FinalAssessment baselineFunctionalCapacity(String baselineFunctionalCapacity) {
        this.baselineFunctionalCapacity = baselineFunctionalCapacity;
        return this;
    }

    public void setBaselineFunctionalCapacity(String baselineFunctionalCapacity) {
        this.baselineFunctionalCapacity = baselineFunctionalCapacity;
    }

    public String getlDL() {
        return lDL;
    }

    public FinalAssessment lDL(String lDL) {
        this.lDL = lDL;
        return this;
    }

    public void setlDL(String lDL) {
        this.lDL = lDL;
    }

    public String gethDL() {
        return hDL;
    }

    public FinalAssessment hDL(String hDL) {
        this.hDL = hDL;
        return this;
    }

    public void sethDL(String hDL) {
        this.hDL = hDL;
    }

    public String getCardiovascularRisk() {
        return cardiovascularRisk;
    }

    public FinalAssessment cardiovascularRisk(String cardiovascularRisk) {
        this.cardiovascularRisk = cardiovascularRisk;
        return this;
    }

    public void setCardiovascularRisk(String cardiovascularRisk) {
        this.cardiovascularRisk = cardiovascularRisk;
    }

    public Boolean isIsWorking() {
        return isWorking;
    }

    public FinalAssessment isWorking(Boolean isWorking) {
        this.isWorking = isWorking;
        return this;
    }

    public void setIsWorking(Boolean isWorking) {
        this.isWorking = isWorking;
    }

    public Boolean isDeceased() {
        return deceased;
    }

    public FinalAssessment deceased(Boolean deceased) {
        this.deceased = deceased;
        return this;
    }

    public void setDeceased(Boolean deceased) {
        this.deceased = deceased;
    }

    public Boolean isAbandonment() {
        return abandonment;
    }

    public FinalAssessment abandonment(Boolean abandonment) {
        this.abandonment = abandonment;
        return this;
    }

    public void setAbandonment(Boolean abandonment) {
        this.abandonment = abandonment;
    }

    public Boolean isAbandonmentMedicCause() {
        return abandonmentMedicCause;
    }

    public FinalAssessment abandonmentMedicCause(Boolean abandonmentMedicCause) {
        this.abandonmentMedicCause = abandonmentMedicCause;
        return this;
    }

    public void setAbandonmentMedicCause(Boolean abandonmentMedicCause) {
        this.abandonmentMedicCause = abandonmentMedicCause;
    }

    public Boolean isHospitalized() {
        return hospitalized;
    }

    public FinalAssessment hospitalized(Boolean hospitalized) {
        this.hospitalized = hospitalized;
        return this;
    }

    public void setHospitalized(Boolean hospitalized) {
        this.hospitalized = hospitalized;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public FinalAssessment deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean isReevaluation() {
        return reevaluation;
    }

    public FinalAssessment reevaluation(Boolean reevaluation) {
        this.reevaluation = reevaluation;
        return this;
    }

    public void setReevaluation(Boolean reevaluation) {
        this.reevaluation = reevaluation;
    }

    public ZonedDateTime getExecutionDate() {
        return executionDate;
    }

    public FinalAssessment executionDate(ZonedDateTime executionDate) {
        this.executionDate = executionDate;
        return this;
    }

    public void setExecutionDate(ZonedDateTime executionDate) {
        this.executionDate = executionDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public FinalAssessment patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FinalAssessment finalAssessment = (FinalAssessment) o;
        if (finalAssessment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), finalAssessment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinalAssessment{" +
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
            "}";
    }
}
