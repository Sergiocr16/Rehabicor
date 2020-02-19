package com.aditum.rehabicor.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RehabilitationGroup.
 */
@Entity
@Table(name = "rehabilitation_group")
public class RehabilitationGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "program_status")
    private Integer programStatus;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToMany
    @JoinTable(name = "rehabilitation_group_patient",
               joinColumns = @JoinColumn(name = "rehabilitation_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
    private Set<Patient> patients = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("rehabilitationGroups")
    private RehabilitationCenter rehabilitationCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public RehabilitationGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public RehabilitationGroup creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getProgramStatus() {
        return programStatus;
    }

    public RehabilitationGroup programStatus(Integer programStatus) {
        this.programStatus = programStatus;
        return this;
    }

    public void setProgramStatus(Integer programStatus) {
        this.programStatus = programStatus;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public RehabilitationGroup deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public RehabilitationGroup patients(Set<Patient> patients) {
        this.patients = patients;
        return this;
    }

    public RehabilitationGroup addPatient(Patient patient) {
        this.patients.add(patient);
        patient.getRehabilitationGroups().add(this);
        return this;
    }

    public RehabilitationGroup removePatient(Patient patient) {
        this.patients.remove(patient);
        patient.getRehabilitationGroups().remove(this);
        return this;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public RehabilitationCenter getRehabilitationCenter() {
        return rehabilitationCenter;
    }

    public RehabilitationGroup rehabilitationCenter(RehabilitationCenter rehabilitationCenter) {
        this.rehabilitationCenter = rehabilitationCenter;
        return this;
    }

    public void setRehabilitationCenter(RehabilitationCenter rehabilitationCenter) {
        this.rehabilitationCenter = rehabilitationCenter;
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
        RehabilitationGroup rehabilitationGroup = (RehabilitationGroup) o;
        if (rehabilitationGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rehabilitationGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RehabilitationGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", programStatus=" + getProgramStatus() +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
