package com.aditum.rehabicor.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RehabilitationCenter.
 */
@Entity
@Table(name = "rehabilitation_center")
public class RehabilitationCenter implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "rehabilitationCenter")
    private Set<AppUser> appUsers = new HashSet<>();
    @OneToMany(mappedBy = "rehabilitationCenter")
    private Set<Comorbiditie> comorbidities = new HashSet<>();
    @OneToMany(mappedBy = "rehabilitationCenter")
    private Set<DepressiveSymptom> depressiveSymptoms = new HashSet<>();
    @OneToMany(mappedBy = "rehabilitationCenter")
    private Set<IncomeDiagnosis> incomeDiagnoses = new HashSet<>();
    @OneToMany(mappedBy = "rehabilitationCenter")
    private Set<MayorEvent> mayorEvents = new HashSet<>();
    @OneToMany(mappedBy = "rehabilitationCenter")
    private Set<MinorEvent> minorEvents = new HashSet<>();
    @OneToMany(mappedBy = "rehabilitationCenter")
    private Set<NonSpecificPain> nonSpecificPains = new HashSet<>();
    @OneToMany(mappedBy = "rehabilitationCenter")
    private Set<RehabilitationGroup> rehabilitationGroups = new HashSet<>();
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

    public RehabilitationCenter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public RehabilitationCenter telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public RehabilitationCenter deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getStatus() {
        return status;
    }

    public RehabilitationCenter status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public RehabilitationCenter appUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
        return this;
    }

    public RehabilitationCenter addAppUser(AppUser appUser) {
        this.appUsers.add(appUser);
        appUser.setRehabilitationCenter(this);
        return this;
    }

    public RehabilitationCenter removeAppUser(AppUser appUser) {
        this.appUsers.remove(appUser);
        appUser.setRehabilitationCenter(null);
        return this;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    public Set<Comorbiditie> getComorbidities() {
        return comorbidities;
    }

    public RehabilitationCenter comorbidities(Set<Comorbiditie> comorbidities) {
        this.comorbidities = comorbidities;
        return this;
    }

    public RehabilitationCenter addComorbiditie(Comorbiditie comorbiditie) {
        this.comorbidities.add(comorbiditie);
        comorbiditie.setRehabilitationCenter(this);
        return this;
    }

    public RehabilitationCenter removeComorbiditie(Comorbiditie comorbiditie) {
        this.comorbidities.remove(comorbiditie);
        comorbiditie.setRehabilitationCenter(null);
        return this;
    }

    public void setComorbidities(Set<Comorbiditie> comorbidities) {
        this.comorbidities = comorbidities;
    }

    public Set<DepressiveSymptom> getDepressiveSymptoms() {
        return depressiveSymptoms;
    }

    public RehabilitationCenter depressiveSymptoms(Set<DepressiveSymptom> depressiveSymptoms) {
        this.depressiveSymptoms = depressiveSymptoms;
        return this;
    }

    public RehabilitationCenter addDepressiveSymptom(DepressiveSymptom depressiveSymptom) {
        this.depressiveSymptoms.add(depressiveSymptom);
        depressiveSymptom.setRehabilitationCenter(this);
        return this;
    }

    public RehabilitationCenter removeDepressiveSymptom(DepressiveSymptom depressiveSymptom) {
        this.depressiveSymptoms.remove(depressiveSymptom);
        depressiveSymptom.setRehabilitationCenter(null);
        return this;
    }

    public void setDepressiveSymptoms(Set<DepressiveSymptom> depressiveSymptoms) {
        this.depressiveSymptoms = depressiveSymptoms;
    }

    public Set<IncomeDiagnosis> getIncomeDiagnoses() {
        return incomeDiagnoses;
    }

    public RehabilitationCenter incomeDiagnoses(Set<IncomeDiagnosis> incomeDiagnoses) {
        this.incomeDiagnoses = incomeDiagnoses;
        return this;
    }

    public RehabilitationCenter addIncomeDiagnosis(IncomeDiagnosis incomeDiagnosis) {
        this.incomeDiagnoses.add(incomeDiagnosis);
        incomeDiagnosis.setRehabilitationCenter(this);
        return this;
    }

    public RehabilitationCenter removeIncomeDiagnosis(IncomeDiagnosis incomeDiagnosis) {
        this.incomeDiagnoses.remove(incomeDiagnosis);
        incomeDiagnosis.setRehabilitationCenter(null);
        return this;
    }

    public void setIncomeDiagnoses(Set<IncomeDiagnosis> incomeDiagnoses) {
        this.incomeDiagnoses = incomeDiagnoses;
    }

    public Set<MayorEvent> getMayorEvents() {
        return mayorEvents;
    }

    public RehabilitationCenter mayorEvents(Set<MayorEvent> mayorEvents) {
        this.mayorEvents = mayorEvents;
        return this;
    }

    public RehabilitationCenter addMayorEvent(MayorEvent mayorEvent) {
        this.mayorEvents.add(mayorEvent);
        mayorEvent.setRehabilitationCenter(this);
        return this;
    }

    public RehabilitationCenter removeMayorEvent(MayorEvent mayorEvent) {
        this.mayorEvents.remove(mayorEvent);
        mayorEvent.setRehabilitationCenter(null);
        return this;
    }

    public void setMayorEvents(Set<MayorEvent> mayorEvents) {
        this.mayorEvents = mayorEvents;
    }

    public Set<MinorEvent> getMinorEvents() {
        return minorEvents;
    }

    public RehabilitationCenter minorEvents(Set<MinorEvent> minorEvents) {
        this.minorEvents = minorEvents;
        return this;
    }

    public RehabilitationCenter addMinorEvent(MinorEvent minorEvent) {
        this.minorEvents.add(minorEvent);
        minorEvent.setRehabilitationCenter(this);
        return this;
    }

    public RehabilitationCenter removeMinorEvent(MinorEvent minorEvent) {
        this.minorEvents.remove(minorEvent);
        minorEvent.setRehabilitationCenter(null);
        return this;
    }

    public void setMinorEvents(Set<MinorEvent> minorEvents) {
        this.minorEvents = minorEvents;
    }

    public Set<NonSpecificPain> getNonSpecificPains() {
        return nonSpecificPains;
    }

    public RehabilitationCenter nonSpecificPains(Set<NonSpecificPain> nonSpecificPains) {
        this.nonSpecificPains = nonSpecificPains;
        return this;
    }

    public RehabilitationCenter addNonSpecificPain(NonSpecificPain nonSpecificPain) {
        this.nonSpecificPains.add(nonSpecificPain);
        nonSpecificPain.setRehabilitationCenter(this);
        return this;
    }

    public RehabilitationCenter removeNonSpecificPain(NonSpecificPain nonSpecificPain) {
        this.nonSpecificPains.remove(nonSpecificPain);
        nonSpecificPain.setRehabilitationCenter(null);
        return this;
    }

    public void setNonSpecificPains(Set<NonSpecificPain> nonSpecificPains) {
        this.nonSpecificPains = nonSpecificPains;
    }

    public Set<RehabilitationGroup> getRehabilitationGroups() {
        return rehabilitationGroups;
    }

    public RehabilitationCenter rehabilitationGroups(Set<RehabilitationGroup> rehabilitationGroups) {
        this.rehabilitationGroups = rehabilitationGroups;
        return this;
    }

    public RehabilitationCenter addRehabilitationGroup(RehabilitationGroup rehabilitationGroup) {
        this.rehabilitationGroups.add(rehabilitationGroup);
        rehabilitationGroup.setRehabilitationCenter(this);
        return this;
    }

    public RehabilitationCenter removeRehabilitationGroup(RehabilitationGroup rehabilitationGroup) {
        this.rehabilitationGroups.remove(rehabilitationGroup);
        rehabilitationGroup.setRehabilitationCenter(null);
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
        RehabilitationCenter rehabilitationCenter = (RehabilitationCenter) o;
        if (rehabilitationCenter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rehabilitationCenter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RehabilitationCenter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
