package com.aditum.rehabicor.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MayorEventsSession.
 */
@Entity
@Table(name = "mayor_events_session")
public class MayorEventsSession implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "exist", nullable = false)
    private Boolean exist;

    @Column(name = "mayor_event_relation")
    private Long mayorEventRelation;

    @ManyToOne
    @JsonIgnoreProperties("mayorEventsSessions")
    private Session session;

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

    public MayorEventsSession description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isExist() {
        return exist;
    }

    public MayorEventsSession exist(Boolean exist) {
        this.exist = exist;
        return this;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    public Long getMayorEventRelation() {
        return mayorEventRelation;
    }

    public MayorEventsSession mayorEventRelation(Long mayorEventRelation) {
        this.mayorEventRelation = mayorEventRelation;
        return this;
    }

    public void setMayorEventRelation(Long mayorEventRelation) {
        this.mayorEventRelation = mayorEventRelation;
    }

    public Session getSession() {
        return session;
    }

    public MayorEventsSession session(Session session) {
        this.session = session;
        return this;
    }

    public void setSession(Session session) {
        this.session = session;
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
        MayorEventsSession mayorEventsSession = (MayorEventsSession) o;
        if (mayorEventsSession.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mayorEventsSession.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MayorEventsSession{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", exist='" + isExist() + "'" +
            ", mayorEventRelation=" + getMayorEventRelation() +
            "}";
    }
}
