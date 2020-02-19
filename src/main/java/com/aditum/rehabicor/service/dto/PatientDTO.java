package com.aditum.rehabicor.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Patient entity.
 */
public class PatientDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Integer age;

    @NotNull
    private String sex;

    @NotNull
    private String ocupation;

    @NotNull
    private Integer lastEventOcurred;

    private Boolean deceased;

    private Boolean abandonment;

    private Boolean abandonmentMedicCause;

    private Integer rehabStatus;

    private Integer sessionNumber;

    private Boolean deleted;

    private String scholarship;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOcupation() {
        return ocupation;
    }

    public void setOcupation(String ocupation) {
        this.ocupation = ocupation;
    }

    public Integer getLastEventOcurred() {
        return lastEventOcurred;
    }

    public void setLastEventOcurred(Integer lastEventOcurred) {
        this.lastEventOcurred = lastEventOcurred;
    }

    public Boolean isDeceased() {
        return deceased;
    }

    public void setDeceased(Boolean deceased) {
        this.deceased = deceased;
    }

    public Boolean isAbandonment() {
        return abandonment;
    }

    public void setAbandonment(Boolean abandonment) {
        this.abandonment = abandonment;
    }

    public Boolean isAbandonmentMedicCause() {
        return abandonmentMedicCause;
    }

    public void setAbandonmentMedicCause(Boolean abandonmentMedicCause) {
        this.abandonmentMedicCause = abandonmentMedicCause;
    }

    public Integer getRehabStatus() {
        return rehabStatus;
    }

    public void setRehabStatus(Integer rehabStatus) {
        this.rehabStatus = rehabStatus;
    }

    public Integer getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(Integer sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getScholarship() {
        return scholarship;
    }

    public void setScholarship(String scholarship) {
        this.scholarship = scholarship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PatientDTO patientDTO = (PatientDTO) o;
        if (patientDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patientDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PatientDTO{" +
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
