package com.aditum.rehabicor.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RehabilitationCenter entity.
 */
public class RehabilitationCenterDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String telephone;

    private Boolean deleted;

    private Integer status;


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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RehabilitationCenterDTO rehabilitationCenterDTO = (RehabilitationCenterDTO) o;
        if (rehabilitationCenterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rehabilitationCenterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RehabilitationCenterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
