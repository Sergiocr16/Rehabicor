package com.aditum.rehabicor.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InitialAssessment.
 */
@Entity
@Table(name = "initial_assessment")
public class InitialAssessment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "smoking", nullable = false)
    private String smoking;

    @NotNull
    @Column(name = "weight", nullable = false)
    private String weight;

    @NotNull
    @Column(name = "jhi_size", nullable = false)
    private String size;

    @NotNull
    @Column(name = "i_mc", nullable = false)
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

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "initialAssessment")
    private Set<ComorbiditiesPatient> comorbiditiesPatients = new HashSet<>();
    @OneToMany(mappedBy = "initialAssessment")
    private Set<IncomeDiagnosisPatient> incomeDiagnosisPatients = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("initialAssessments")
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

    public InitialAssessment smoking(String smoking) {
        this.smoking = smoking;
        return this;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getWeight() {
        return weight;
    }

    public InitialAssessment weight(String weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public InitialAssessment size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getiMC() {
        return iMC;
    }

    public InitialAssessment iMC(String iMC) {
        this.iMC = iMC;
        return this;
    }

    public void setiMC(String iMC) {
        this.iMC = iMC;
    }

    public String getHbiac() {
        return hbiac;
    }

    public InitialAssessment hbiac(String hbiac) {
        this.hbiac = hbiac;
        return this;
    }

    public void setHbiac(String hbiac) {
        this.hbiac = hbiac;
    }

    public String getBaselineFunctionalCapacity() {
        return baselineFunctionalCapacity;
    }

    public InitialAssessment baselineFunctionalCapacity(String baselineFunctionalCapacity) {
        this.baselineFunctionalCapacity = baselineFunctionalCapacity;
        return this;
    }

    public void setBaselineFunctionalCapacity(String baselineFunctionalCapacity) {
        this.baselineFunctionalCapacity = baselineFunctionalCapacity;
    }

    public String getlDL() {
        return lDL;
    }

    public InitialAssessment lDL(String lDL) {
        this.lDL = lDL;
        return this;
    }

    public void setlDL(String lDL) {
        this.lDL = lDL;
    }

    public String gethDL() {
        return hDL;
    }

    public InitialAssessment hDL(String hDL) {
        this.hDL = hDL;
        return this;
    }

    public void sethDL(String hDL) {
        this.hDL = hDL;
    }

    public String getCardiovascularRisk() {
        return cardiovascularRisk;
    }

    public InitialAssessment cardiovascularRisk(String cardiovascularRisk) {
        this.cardiovascularRisk = cardiovascularRisk;
        return this;
    }

    public void setCardiovascularRisk(String cardiovascularRisk) {
        this.cardiovascularRisk = cardiovascularRisk;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public InitialAssessment deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<ComorbiditiesPatient> getComorbiditiesPatients() {
        return comorbiditiesPatients;
    }

    public InitialAssessment comorbiditiesPatients(Set<ComorbiditiesPatient> comorbiditiesPatients) {
        this.comorbiditiesPatients = comorbiditiesPatients;
        return this;
    }

    public InitialAssessment addComorbiditiesPatient(ComorbiditiesPatient comorbiditiesPatient) {
        this.comorbiditiesPatients.add(comorbiditiesPatient);
        comorbiditiesPatient.setInitialAssessment(this);
        return this;
    }

    public InitialAssessment removeComorbiditiesPatient(ComorbiditiesPatient comorbiditiesPatient) {
        this.comorbiditiesPatients.remove(comorbiditiesPatient);
        comorbiditiesPatient.setInitialAssessment(null);
        return this;
    }

    public void setComorbiditiesPatients(Set<ComorbiditiesPatient> comorbiditiesPatients) {
        this.comorbiditiesPatients = comorbiditiesPatients;
    }

    public Set<IncomeDiagnosisPatient> getIncomeDiagnosisPatients() {
        return incomeDiagnosisPatients;
    }

    public InitialAssessment incomeDiagnosisPatients(Set<IncomeDiagnosisPatient> incomeDiagnosisPatients) {
        this.incomeDiagnosisPatients = incomeDiagnosisPatients;
        return this;
    }

    public InitialAssessment addIncomeDiagnosisPatient(IncomeDiagnosisPatient incomeDiagnosisPatient) {
        this.incomeDiagnosisPatients.add(incomeDiagnosisPatient);
        incomeDiagnosisPatient.setInitialAssessment(this);
        return this;
    }

    public InitialAssessment removeIncomeDiagnosisPatient(IncomeDiagnosisPatient incomeDiagnosisPatient) {
        this.incomeDiagnosisPatients.remove(incomeDiagnosisPatient);
        incomeDiagnosisPatient.setInitialAssessment(null);
        return this;
    }

    public void setIncomeDiagnosisPatients(Set<IncomeDiagnosisPatient> incomeDiagnosisPatients) {
        this.incomeDiagnosisPatients = incomeDiagnosisPatients;
    }

    public Patient getPatient() {
        return patient;
    }

    public InitialAssessment patient(Patient patient) {
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
        InitialAssessment initialAssessment = (InitialAssessment) o;
        if (initialAssessment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), initialAssessment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InitialAssessment{" +
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
            "}";
    }
}
