package com.aditum.rehabicor.service.dto;

import java.io.Serializable;
import java.util.List;

public class SesionDistributionDTO implements Serializable {

    String sesionNumber;

    Integer minorEventsPerSesion;

    Integer mayorEventsPerSesion;

    List<MinorEventsSessionDTO> minorEvents;

    List<MayorEventsSessionDTO> mayorEvents;

    public String getSesionNumber() {
        return sesionNumber;
    }

    public void setSesionNumber(String sesionNumber) {
        this.sesionNumber = sesionNumber;
    }

    public Integer getMinorEventsPerSesion() {
        return minorEventsPerSesion;
    }

    public void setMinorEventsPerSesion(Integer minorEventsPerSesion) {
        this.minorEventsPerSesion = minorEventsPerSesion;
    }

    public Integer getMayorEventsPerSesion() {
        return mayorEventsPerSesion;
    }

    public void setMayorEventsPerSesion(Integer mayorEventsPerSesion) {
        this.mayorEventsPerSesion = mayorEventsPerSesion;
    }

    public List<MinorEventsSessionDTO> getMinorEvents() {
        return minorEvents;
    }

    public void setMinorEvents(List<MinorEventsSessionDTO> minorEvents) {
        this.minorEvents = minorEvents;
    }

    public List<MayorEventsSessionDTO> getMayorEvents() {
        return mayorEvents;
    }

    public void setMayorEvents(List<MayorEventsSessionDTO> mayorEvents) {
        this.mayorEvents = mayorEvents;
    }
}
