package com.aditum.rehabicor.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupCharacteristicsDTO implements Serializable {


    private int averageAge;

    private int averageLastEvent;

    private List<Integer> genderDistribution;

    private List<Integer> scholarityDistribution;

    private int tabaquismPercentaje;

    private List<IncomeDiagnosisDTO> incomeDiagnosisDistribution;

    private List<ComorbiditieDTO> comorbiditieDistribution;

    private List<Integer> distributionIMC;

    private double averageHb1Ac;

    private double averageFunctionalCapacity;

    private List<Integer> cardiovascularRiskDistribution;

    private RehabilitationGroupDTO rehabilitationGroupDTO;

    public RehabilitationGroupDTO getRehabilitationGroupDTO() {
        return rehabilitationGroupDTO;
    }

    public void setRehabilitationGroupDTO(RehabilitationGroupDTO rehabilitationGroupDTO) {
        this.rehabilitationGroupDTO = rehabilitationGroupDTO;
    }

    public GroupCharacteristicsDTO() {
        this.genderDistribution = new ArrayList<>();
//        ' 0 - Masculino', ' 1 - Femenino'
        this.genderDistribution.add(0);
        this.genderDistribution.add(0);

        this.scholarityDistribution = new ArrayList<>();
//            '0 - Ninguna',
//            '1 - Primaria Incompleta',
//            '2 - Primaria completa',
//            '3 - Secundaria Incompleta',
//            '4 - Secundaria completa',
//            '5 - Universitaria Incompleta',
//            '6 - Universitaria completa',
//            '7 - Parauniversitaria'
        this.scholarityDistribution.add(0);
        this.scholarityDistribution.add(0);
        this.scholarityDistribution.add(0);
        this.scholarityDistribution.add(0);
        this.scholarityDistribution.add(0);
        this.scholarityDistribution.add(0);
        this.scholarityDistribution.add(0);
        this.scholarityDistribution.add(0);

        this.incomeDiagnosisDistribution = new ArrayList<>();
        this.comorbiditieDistribution = new ArrayList<>();

        this.distributionIMC = new ArrayList<>();
//     -Promedio de IMC  en rangos de [0](-19)delgadez  [1](20-25)normal  [2](26-30)sobrepeso [3](30 o mas) obesidad
        this.distributionIMC.add(0);
        this.distributionIMC.add(0);
        this.distributionIMC.add(0);
        this.distributionIMC.add(0);

        this.cardiovascularRiskDistribution = new ArrayList<>();
//        '0 - Alto', '1 - Moderado', '2 - Bajo'
        this.cardiovascularRiskDistribution.add(0);
        this.cardiovascularRiskDistribution.add(0);
        this.cardiovascularRiskDistribution.add(0);
    }


    public int getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(int averageAge) {
        this.averageAge = averageAge;
    }

    public int getAverageLastEvent() {
        return averageLastEvent;
    }

    public void setAverageLastEvent(int averageLastEvent) {
        this.averageLastEvent = averageLastEvent;
    }

    public List<Integer> getGenderDistribution() {
        return genderDistribution;
    }

    public void setGenderDistribution(List<Integer> genderDistribution) {
        this.genderDistribution = genderDistribution;
    }

    public List<Integer> getScholarityDistribution() {
        return scholarityDistribution;
    }

    public void setScholarityDistribution(List<Integer> scholarityDistribution) {
        this.scholarityDistribution = scholarityDistribution;
    }

    public int getTabaquismPercentaje() {
        return tabaquismPercentaje;
    }

    public void setTabaquismPercentaje(int tabaquismPercentaje) {
        this.tabaquismPercentaje = tabaquismPercentaje;
    }

    public List<IncomeDiagnosisDTO> getIncomeDiagnosisDistribution() {
        return incomeDiagnosisDistribution;
    }

    public void setIncomeDiagnosisDistribution(List<IncomeDiagnosisDTO> incomeDiagnosisDistribution) {
        this.incomeDiagnosisDistribution = incomeDiagnosisDistribution;
    }

    public List<ComorbiditieDTO> getComorbiditieDistribution() {
        return comorbiditieDistribution;
    }

    public void setComorbiditieDistribution(List<ComorbiditieDTO> comorbiditieDistribution) {
        this.comorbiditieDistribution = comorbiditieDistribution;
    }

    public List<Integer> getDistributionIMC() {
        return distributionIMC;
    }

    public void setDistributionIMC(List<Integer> distributionIMC) {
        this.distributionIMC = distributionIMC;
    }

    public double getAverageHb1Ac() {
        return averageHb1Ac;
    }

    public void setAverageHb1Ac(double averageHb1Ac) {
        this.averageHb1Ac = averageHb1Ac;
    }

    public double getAverageFunctionalCapacity() {
        return averageFunctionalCapacity;
    }

    public void setAverageFunctionalCapacity(double averageFunctionalCapacity) {
        this.averageFunctionalCapacity = averageFunctionalCapacity;
    }

    public List<Integer> getCardiovascularRiskDistribution() {
        return cardiovascularRiskDistribution;
    }

    public void setCardiovascularRiskDistribution(List<Integer> cardiovascularRiskDistribution) {
        this.cardiovascularRiskDistribution = cardiovascularRiskDistribution;
    }


    public void increaseSex(int pos){
        this.getGenderDistribution().set(pos,this.getGenderDistribution().get(pos)+1);
    }
    public void increaseScholarity(int pos){
        this.getScholarityDistribution().set(pos,this.getScholarityDistribution().get(pos)+1);
    }

    public void increaseCardiovascularRisk(int pos){
        this.getCardiovascularRiskDistribution().set(pos,this.getCardiovascularRiskDistribution().get(pos)+1);
    }

    public void increaseIMC(int pos){
        this.getDistributionIMC().set(pos,this.getDistributionIMC().get(pos)+1);
    }
}
