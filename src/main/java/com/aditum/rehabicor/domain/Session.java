package com.aditum.rehabicor.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Session.
 */
@Entity
@Table(name = "session")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "execution_date", nullable = false)
    private Instant executionDate;

    @Column(name = "abscence")
    private Boolean abscence;

    @Column(name = "hospitalization")
    private Boolean hospitalization;

    @Column(name = "status")
    private Integer status;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "currently_working")
    private Boolean currentlyWorking;

    @OneToMany(mappedBy = "session")
    private Set<DepressiveSymptomsSession> depressiveSymptomsSessions = new HashSet<>();
    @OneToMany(mappedBy = "session")
    private Set<MayorEventsSession> mayorEventsSessions = new HashSet<>();
    @OneToMany(mappedBy = "session")
    private Set<MinorEventsSession> minorEventsSessions = new HashSet<>();
    @OneToMany(mappedBy = "session")
    private Set<NonSpecificPainsSession> nonSpecificPainsSessions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Session code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getExecutionDate() {
        return executionDate;
    }

    public Session executionDate(Instant executionDate) {
        this.executionDate = executionDate;
        return this;
    }

    public void setExecutionDate(Instant executionDate) {
        this.executionDate = executionDate;
    }

    public Boolean isAbscence() {
        return abscence;
    }

    public Session abscence(Boolean abscence) {
        this.abscence = abscence;
        return this;
    }

    public void setAbscence(Boolean abscence) {
        this.abscence = abscence;
    }

    public Boolean isHospitalization() {
        return hospitalization;
    }

    public Session hospitalization(Boolean hospitalization) {
        this.hospitalization = hospitalization;
        return this;
    }

    public void setHospitalization(Boolean hospitalization) {
        this.hospitalization = hospitalization;
    }

    public Integer getStatus() {
        return status;
    }

    public Session status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Session deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean isCurrentlyWorking() {
        return currentlyWorking;
    }

    public Session currentlyWorking(Boolean currentlyWorking) {
        this.currentlyWorking = currentlyWorking;
        return this;
    }

    public void setCurrentlyWorking(Boolean currentlyWorking) {
        this.currentlyWorking = currentlyWorking;
    }

    public Set<DepressiveSymptomsSession> getDepressiveSymptomsSessions() {
        return depressiveSymptomsSessions;
    }

    public Session depressiveSymptomsSessions(Set<DepressiveSymptomsSession> depressiveSymptomsSessions) {
        this.depressiveSymptomsSessions = depressiveSymptomsSessions;
        return this;
    }

    public Session addDepressiveSymptomsSession(DepressiveSymptomsSession depressiveSymptomsSession) {
        this.depressiveSymptomsSessions.add(depressiveSymptomsSession);
        depressiveSymptomsSession.setSession(this);
        return this;
    }

    public Session removeDepressiveSymptomsSession(DepressiveSymptomsSession depressiveSymptomsSession) {
        this.depressiveSymptomsSessions.remove(depressiveSymptomsSession);
        depressiveSymptomsSession.setSession(null);
        return this;
    }

    public void setDepressiveSymptomsSessions(Set<DepressiveSymptomsSession> depressiveSymptomsSessions) {
        this.depressiveSymptomsSessions = depressiveSymptomsSessions;
    }

    public Set<MayorEventsSession> getMayorEventsSessions() {
        return mayorEventsSessions;
    }

    public Session mayorEventsSessions(Set<MayorEventsSession> mayorEventsSessions) {
        this.mayorEventsSessions = mayorEventsSessions;
        return this;
    }

    public Session addMayorEventsSession(MayorEventsSession mayorEventsSession) {
        this.mayorEventsSessions.add(mayorEventsSession);
        mayorEventsSession.setSession(this);
        return this;
    }

    public Session removeMayorEventsSession(MayorEventsSession mayorEventsSession) {
        this.mayorEventsSessions.remove(mayorEventsSession);
        mayorEventsSession.setSession(null);
        return this;
    }

    public void setMayorEventsSessions(Set<MayorEventsSession> mayorEventsSessions) {
        this.mayorEventsSessions = mayorEventsSessions;
    }

    public Set<MinorEventsSession> getMinorEventsSessions() {
        return minorEventsSessions;
    }

    public Session minorEventsSessions(Set<MinorEventsSession> minorEventsSessions) {
        this.minorEventsSessions = minorEventsSessions;
        return this;
    }

    public Session addMinorEventsSession(MinorEventsSession minorEventsSession) {
        this.minorEventsSessions.add(minorEventsSession);
        minorEventsSession.setSession(this);
        return this;
    }

    public Session removeMinorEventsSession(MinorEventsSession minorEventsSession) {
        this.minorEventsSessions.remove(minorEventsSession);
        minorEventsSession.setSession(null);
        return this;
    }

    public void setMinorEventsSessions(Set<MinorEventsSession> minorEventsSessions) {
        this.minorEventsSessions = minorEventsSessions;
    }

    public Set<NonSpecificPainsSession> getNonSpecificPainsSessions() {
        return nonSpecificPainsSessions;
    }

    public Session nonSpecificPainsSessions(Set<NonSpecificPainsSession> nonSpecificPainsSessions) {
        this.nonSpecificPainsSessions = nonSpecificPainsSessions;
        return this;
    }

    public Session addNonSpecificPainsSession(NonSpecificPainsSession nonSpecificPainsSession) {
        this.nonSpecificPainsSessions.add(nonSpecificPainsSession);
        nonSpecificPainsSession.setSession(this);
        return this;
    }

    public Session removeNonSpecificPainsSession(NonSpecificPainsSession nonSpecificPainsSession) {
        this.nonSpecificPainsSessions.remove(nonSpecificPainsSession);
        nonSpecificPainsSession.setSession(null);
        return this;
    }

    public void setNonSpecificPainsSessions(Set<NonSpecificPainsSession> nonSpecificPainsSessions) {
        this.nonSpecificPainsSessions = nonSpecificPainsSessions;
    }

    public Patient getPatient() {
        return patient;
    }

    public Session patient(Patient patient) {
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
        Session session = (Session) o;
        if (session.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), session.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", executionDate='" + getExecutionDate() + "'" +
            ", abscence='" + isAbscence() + "'" +
            ", hospitalization='" + isHospitalization() + "'" +
            ", status=" + getStatus() +
            ", deleted='" + isDeleted() + "'" +
            ", currentlyWorking='" + isCurrentlyWorking() + "'" +
            "}";
    }
}
