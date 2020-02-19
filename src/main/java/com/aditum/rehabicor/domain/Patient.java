package com.aditum.rehabicor.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Column(name = "sex", nullable = false)
    private String sex;

    @NotNull
    @Column(name = "ocupation", nullable = false)
    private String ocupation;

    @NotNull
    @Column(name = "last_event_ocurred", nullable = false)
    private Integer lastEventOcurred;

    @Column(name = "deceased")
    private Boolean deceased;

    @Column(name = "abandonment")
    private Boolean abandonment;

    @Column(name = "abandonment_medic_cause")
    private Boolean abandonmentMedicCause;

    @Column(name = "rehab_status")
    private Integer rehabStatus;

    @Column(name = "session_number")
    private Integer sessionNumber;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "scholarship")
    private String scholarship;

    @OneToMany(mappedBy = "patient")
    private Set<FinalAssessment> finalAssessments = new HashSet<>();
    @OneToMany(mappedBy = "patient")
    private Set<InitialAssessment> initialAssessments = new HashSet<>();
    @OneToMany(mappedBy = "patient")
    private Set<Session> sessions = new HashSet<>();
    @ManyToMany(mappedBy = "patients")
    @JsonIgnore
    private Set<RehabilitationGroup> rehabilitationGroups = new HashSet<>();

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

    public Patient code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAge() {
        return age;
    }

    public Patient age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public Patient sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOcupation() {
        return ocupation;
    }

    public Patient ocupation(String ocupation) {
        this.ocupation = ocupation;
        return this;
    }

    public void setOcupation(String ocupation) {
        this.ocupation = ocupation;
    }

    public Integer getLastEventOcurred() {
        return lastEventOcurred;
    }

    public Patient lastEventOcurred(Integer lastEventOcurred) {
        this.lastEventOcurred = lastEventOcurred;
        return this;
    }

    public void setLastEventOcurred(Integer lastEventOcurred) {
        this.lastEventOcurred = lastEventOcurred;
    }

    public Boolean isDeceased() {
        return deceased;
    }

    public Patient deceased(Boolean deceased) {
        this.deceased = deceased;
        return this;
    }

    public void setDeceased(Boolean deceased) {
        this.deceased = deceased;
    }

    public Boolean isAbandonment() {
        return abandonment;
    }

    public Patient abandonment(Boolean abandonment) {
        this.abandonment = abandonment;
        return this;
    }

    public void setAbandonment(Boolean abandonment) {
        this.abandonment = abandonment;
    }

    public Boolean isAbandonmentMedicCause() {
        return abandonmentMedicCause;
    }

    public Patient abandonmentMedicCause(Boolean abandonmentMedicCause) {
        this.abandonmentMedicCause = abandonmentMedicCause;
        return this;
    }

    public void setAbandonmentMedicCause(Boolean abandonmentMedicCause) {
        this.abandonmentMedicCause = abandonmentMedicCause;
    }

    public Integer getRehabStatus() {
        return rehabStatus;
    }

    public Patient rehabStatus(Integer rehabStatus) {
        this.rehabStatus = rehabStatus;
        return this;
    }

    public void setRehabStatus(Integer rehabStatus) {
        this.rehabStatus = rehabStatus;
    }

    public Integer getSessionNumber() {
        return sessionNumber;
    }

    public Patient sessionNumber(Integer sessionNumber) {
        this.sessionNumber = sessionNumber;
        return this;
    }

    public void setSessionNumber(Integer sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Patient deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getScholarship() {
        return scholarship;
    }

    public Patient scholarship(String scholarship) {
        this.scholarship = scholarship;
        return this;
    }

    public void setScholarship(String scholarship) {
        this.scholarship = scholarship;
    }

    public Set<FinalAssessment> getFinalAssessments() {
        return finalAssessments;
    }

    public Patient finalAssessments(Set<FinalAssessment> finalAssessments) {
        this.finalAssessments = finalAssessments;
        return this;
    }

    public Patient addFinalAssessment(FinalAssessment finalAssessment) {
        this.finalAssessments.add(finalAssessment);
        finalAssessment.setPatient(this);
        return this;
    }

    public Patient removeFinalAssessment(FinalAssessment finalAssessment) {
        this.finalAssessments.remove(finalAssessment);
        finalAssessment.setPatient(null);
        return this;
    }

    public void setFinalAssessments(Set<FinalAssessment> finalAssessments) {
        this.finalAssessments = finalAssessments;
    }

    public Set<InitialAssessment> getInitialAssessments() {
        return initialAssessments;
    }

    public Patient initialAssessments(Set<InitialAssessment> initialAssessments) {
        this.initialAssessments = initialAssessments;
        return this;
    }

    public Patient addInitialAssessment(InitialAssessment initialAssessment) {
        this.initialAssessments.add(initialAssessment);
        initialAssessment.setPatient(this);
        return this;
    }

    public Patient removeInitialAssessment(InitialAssessment initialAssessment) {
        this.initialAssessments.remove(initialAssessment);
        initialAssessment.setPatient(null);
        return this;
    }

    public void setInitialAssessments(Set<InitialAssessment> initialAssessments) {
        this.initialAssessments = initialAssessments;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public Patient sessions(Set<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public Patient addSession(Session session) {
        this.sessions.add(session);
        session.setPatient(this);
        return this;
    }

    public Patient removeSession(Session session) {
        this.sessions.remove(session);
        session.setPatient(null);
        return this;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public Set<RehabilitationGroup> getRehabilitationGroups() {
        return rehabilitationGroups;
    }

    public Patient rehabilitationGroups(Set<RehabilitationGroup> rehabilitationGroups) {
        this.rehabilitationGroups = rehabilitationGroups;
        return this;
    }

    public Patient addRehabilitationGroup(RehabilitationGroup rehabilitationGroup) {
        this.rehabilitationGroups.add(rehabilitationGroup);
        rehabilitationGroup.getPatients().add(this);
        return this;
    }

    public Patient removeRehabilitationGroup(RehabilitationGroup rehabilitationGroup) {
        this.rehabilitationGroups.remove(rehabilitationGroup);
        rehabilitationGroup.getPatients().remove(this);
        return this;
    }

    public void setRehabilitationGroups(Set<RehabilitationGroup> rehabilitationGroups) {
        this.rehabilitationGroups = rehabilitationGroups;
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
        Patient patient = (Patient) o;
        if (patient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", age=" + getAge() +
            ", sex='" + getSex() + "'" +
            ", ocupation='" + getOcupation() + "'" +
            ", lastEventOcurred=" + getLastEventOcurred() +
            ", deceased='" + isDeceased() + "'" +
            ", abandonment='" + isAbandonment() + "'" +
            ", abandonmentMedicCause='" + isAbandonmentMedicCause() + "'" +
            ", rehabStatus=" + getRehabStatus() +
            ", sessionNumber=" + getSessionNumber() +
            ", deleted='" + isDeleted() + "'" +
            ", scholarship='" + getScholarship() + "'" +
            "}";
    }
}
