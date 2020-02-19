package com.aditum.rehabicor.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ComorbiditiesPatient.
 */
@Entity
@Table(name = "comorbidities_patient")
public class ComorbiditiesPatient implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "exist", nullable = false)
    private Boolean exist;

    @Column(name = "comorbiditie_relation")
    private Long comorbiditieRelation;

    @ManyToOne
    @JsonIgnoreProperties("comorbiditiesPatients")
    private InitialAssessment initialAssessment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public ComorbiditiesPatient description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isExist() {
        return exist;
    }

    public ComorbiditiesPatient exist(Boolean exist) {
        this.exist = exist;
        return this;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    public Long getComorbiditieRelation() {
        return comorbiditieRelation;
    }

    public ComorbiditiesPatient comorbiditieRelation(Long comorbiditieRelation) {
        this.comorbiditieRelation = comorbiditieRelation;
        return this;
    }

    public void setComorbiditieRelation(Long comorbiditieRelation) {
        this.comorbiditieRelation = comorbiditieRelation;
    }

    public InitialAssessment getInitialAssessment() {
        return initialAssessment;
    }

    public ComorbiditiesPatient initialAssessment(InitialAssessment initialAssessment) {
        this.initialAssessment = initialAssessment;
        return this;
    }

    public void setInitialAssessment(InitialAssessment initialAssessment) {
        this.initialAssessment = initialAssessment;
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
        ComorbiditiesPatient comorbiditiesPatient = (ComorbiditiesPatient) o;
        if (comorbiditiesPatient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comorbiditiesPatient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComorbiditiesPatient{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", exist='" + isExist() + "'" +
            ", comorbiditieRelation=" + getComorbiditieRelation() +
            "}";
    }
}
