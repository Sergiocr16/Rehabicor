package com.aditum.rehabicor.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MinorEventsSession.
 */
@Entity
@Table(name = "minor_events_session")
public class MinorEventsSession implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "exist", nullable = false)
    private Boolean exist;

    @Column(name = "minor_event_relation")
    private Long minorEventRelation;

    @ManyToOne
    @JsonIgnoreProperties("minorEventsSessions")
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

    public MinorEventsSession description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isExist() {
        return exist;
    }

    public MinorEventsSession exist(Boolean exist) {
        this.exist = exist;
        return this;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    public Long getMinorEventRelation() {
        return minorEventRelation;
    }

    public MinorEventsSession minorEventRelation(Long minorEventRelation) {
        this.minorEventRelation = minorEventRelation;
        return this;
    }

    public void setMinorEventRelation(Long minorEventRelation) {
        this.minorEventRelation = minorEventRelation;
    }

    public Session getSession() {
        return session;
    }

    public MinorEventsSession session(Session session) {
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
        MinorEventsSession minorEventsSession = (MinorEventsSession) o;
        if (minorEventsSession.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), minorEventsSession.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MinorEventsSession{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", exist='" + isExist() + "'" +
            ", minorEventRelation=" + getMinorEventRelation() +
            "}";
    }
}
