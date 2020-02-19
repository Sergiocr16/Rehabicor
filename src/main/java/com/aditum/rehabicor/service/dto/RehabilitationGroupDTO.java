package com.aditum.rehabicor.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RehabilitationGroup entity.
 */
public class RehabilitationGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Instant creationDate;

    private Integer programStatus;

    private Boolean deleted;


    private Set<PatientDTO> patients = new HashSet<>();

    private Long rehabilitationCenterId;

    private PanelDataDTO panelData;

    public PanelDataDTO getPanelData() {
        return panelData;
    }

    public void setPanelData(PanelDataDTO panelData) {
        this.panelData = panelData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getProgramStatus() {
        return programStatus;
    }

    public void setProgramStatus(Integer programStatus) {
        this.programStatus = programStatus;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<PatientDTO> getPatients() {
        return patients;
    }

    public void setPatients(Set<PatientDTO> patients) {
        this.patients = patients;
    }

    public Long getRehabilitationCenterId() {
        return rehabilitationCenterId;
    }

    public void setRehabilitationCenterId(Long rehabilitationCenterId) {
        this.rehabilitationCenterId = rehabilitationCenterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RehabilitationGroupDTO rehabilitationGroupDTO = (RehabilitationGroupDTO) o;
        if (rehabilitationGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rehabilitationGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RehabilitationGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", programStatus=" + getProgramStatus() +
            ", deleted='" + isDeleted() + "'" +
            ", rehabilitationCenter=" + getRehabilitationCenterId() +
            "}";
    }
}
