package com.aditum.rehabicor.service.dto;

import java.io.Serializable;
import java.util.List;

public class PanelDataDTO implements Serializable {

    Double fullSelectionCriteriaPercentage;
    Double fullSelectionCriteriaRestPercentage;

    Double abandonmentNonMedicalCausePercentage;
    Double abandonmentNonMedicalCauseRestPercentage;


    Double assessmentIndividualGroupSupportPercentage;
    Double assessmentIndividualGroupSupportRestPercentage;

    Double improvementFunctionalCapacityPercentage;
    Double improvementFunctionalCapacityRestPercentage;

    Double improvementLipidicProfilePercentage;
    Double improvementLipidicProfileRestPercentage;

    Double improvementGlycemicControlPercentage;
    Double improvementGlycemicControlRestPercentage;

    Double weightReductionPercentage;
    Double weightReductionRestPercentage;

//    ?????????
    List<SesionDistributionDTO> distributionMinorEvents;

//    ?????????
     List<SesionDistributionDTO> distributionMayorEvents;

    Double suspendedSmokingPercentage;
    Double suspendedSmokingRestPercentage;

//    ??????????
    Double followUpExternalPercentage;
    Double followUpExternalRestPercentage;

    Double returnWorkActivityPercentage;
    Double returnWorkActivityRestPercentage;

    Double exerciseAdherencePercentage;
    Double exerciseAdherenceRestPercentage;

    Integer patientsTotal;

    public Integer getPatientsTotal() {
        return patientsTotal;
    }

    public void setPatientsTotal(Integer patientsTotal) {
        this.patientsTotal = patientsTotal;
    }


    public Double getFullSelectionCriteriaPercentage() {
        return fullSelectionCriteriaPercentage;
    }

    public void setFullSelectionCriteriaPercentage(Double fullSelectionCriteriaPercentage) {
        this.fullSelectionCriteriaPercentage = fullSelectionCriteriaPercentage;
    }

    public Double getFullSelectionCriteriaRestPercentage() {
        return fullSelectionCriteriaRestPercentage;
    }

    public List<SesionDistributionDTO> getDistributionMinorEvents() {
        return distributionMinorEvents;
    }

    public void setDistributionMinorEvents(List<SesionDistributionDTO> distributionMinorEvents) {
        this.distributionMinorEvents = distributionMinorEvents;
    }

    public List<SesionDistributionDTO> getDistributionMayorEvents() {
        return distributionMayorEvents;
    }

    public void setDistributionMayorEvents(List<SesionDistributionDTO> distributionMayorEvents) {
        this.distributionMayorEvents = distributionMayorEvents;
    }

    public void setFullSelectionCriteriaRestPercentage(Double fullSelectionCriteriaRestPercentage) {
        this.fullSelectionCriteriaRestPercentage = fullSelectionCriteriaRestPercentage;
    }

    public Double getAbandonmentNonMedicalCausePercentage() {
        return abandonmentNonMedicalCausePercentage;
    }

    public void setAbandonmentNonMedicalCausePercentage(Double abandonmentNonMedicalCausePercentage) {
        this.abandonmentNonMedicalCausePercentage = abandonmentNonMedicalCausePercentage;
    }

    public Double getAbandonmentNonMedicalCauseRestPercentage() {
        return abandonmentNonMedicalCauseRestPercentage;
    }

    public void setAbandonmentNonMedicalCauseRestPercentage(Double abandonmentNonMedicalCauseRestPercentage) {
        this.abandonmentNonMedicalCauseRestPercentage = abandonmentNonMedicalCauseRestPercentage;
    }

    public Double getAssessmentIndividualGroupSupportPercentage() {
        return assessmentIndividualGroupSupportPercentage;
    }

    public void setAssessmentIndividualGroupSupportPercentage(Double assessmentIndividualGroupSupportPercentage) {
        this.assessmentIndividualGroupSupportPercentage = assessmentIndividualGroupSupportPercentage;
    }

    public Double getAssessmentIndividualGroupSupportRestPercentage() {
        return assessmentIndividualGroupSupportRestPercentage;
    }

    public void setAssessmentIndividualGroupSupportRestPercentage(Double assessmentIndividualGroupSupportRestPercentage) {
        this.assessmentIndividualGroupSupportRestPercentage = assessmentIndividualGroupSupportRestPercentage;
    }

    public Double getImprovementFunctionalCapacityPercentage() {
        return improvementFunctionalCapacityPercentage;
    }

    public void setImprovementFunctionalCapacityPercentage(Double improvementFunctionalCapacityPercentage) {
        this.improvementFunctionalCapacityPercentage = improvementFunctionalCapacityPercentage;
    }

    public Double getImprovementFunctionalCapacityRestPercentage() {
        return improvementFunctionalCapacityRestPercentage;
    }

    public void setImprovementFunctionalCapacityRestPercentage(Double improvementFunctionalCapacityRestPercentage) {
        this.improvementFunctionalCapacityRestPercentage = improvementFunctionalCapacityRestPercentage;
    }

    public Double getImprovementLipidicProfilePercentage() {
        return improvementLipidicProfilePercentage;
    }

    public void setImprovementLipidicProfilePercentage(Double improvementLipidicProfilePercentage) {
        this.improvementLipidicProfilePercentage = improvementLipidicProfilePercentage;
    }

    public Double getImprovementLipidicProfileRestPercentage() {
        return improvementLipidicProfileRestPercentage;
    }

    public void setImprovementLipidicProfileRestPercentage(Double improvementLipidicProfileRestPercentage) {
        this.improvementLipidicProfileRestPercentage = improvementLipidicProfileRestPercentage;
    }

    public Double getImprovementGlycemicControlPercentage() {
        return improvementGlycemicControlPercentage;
    }

    public void setImprovementGlycemicControlPercentage(Double improvementGlycemicControlPercentage) {
        this.improvementGlycemicControlPercentage = improvementGlycemicControlPercentage;
    }

    public Double getImprovementGlycemicControlRestPercentage() {
        return improvementGlycemicControlRestPercentage;
    }

    public void setImprovementGlycemicControlRestPercentage(Double improvementGlycemicControlRestPercentage) {
        this.improvementGlycemicControlRestPercentage = improvementGlycemicControlRestPercentage;
    }

    public Double getWeightReductionPercentage() {
        return weightReductionPercentage;
    }

    public void setWeightReductionPercentage(Double weightReductionPercentage) {
        this.weightReductionPercentage = weightReductionPercentage;
    }

    public Double getWeightReductionRestPercentage() {
        return weightReductionRestPercentage;
    }

    public void setWeightReductionRestPercentage(Double weightReductionRestPercentage) {
        this.weightReductionRestPercentage = weightReductionRestPercentage;
    }


    public Double getSuspendedSmokingPercentage() {
        return suspendedSmokingPercentage;
    }

    public void setSuspendedSmokingPercentage(Double suspendedSmokingPercentage) {
        this.suspendedSmokingPercentage = suspendedSmokingPercentage;
    }

    public Double getSuspendedSmokingRestPercentage() {
        return suspendedSmokingRestPercentage;
    }

    public void setSuspendedSmokingRestPercentage(Double suspendedSmokingRestPercentage) {
        this.suspendedSmokingRestPercentage = suspendedSmokingRestPercentage;
    }

    public Double getFollowUpExternalPercentage() {
        return followUpExternalPercentage;
    }

    public void setFollowUpExternalPercentage(Double followUpExternalPercentage) {
        this.followUpExternalPercentage = followUpExternalPercentage;
    }

    public Double getFollowUpExternalRestPercentage() {
        return followUpExternalRestPercentage;
    }

    public void setFollowUpExternalRestPercentage(Double followUpExternalRestPercentage) {
        this.followUpExternalRestPercentage = followUpExternalRestPercentage;
    }

    public Double getReturnWorkActivityPercentage() {
        return returnWorkActivityPercentage;
    }

    public void setReturnWorkActivityPercentage(Double returnWorkActivityPercentage) {
        this.returnWorkActivityPercentage = returnWorkActivityPercentage;
    }

    public Double getReturnWorkActivityRestPercentage() {
        return returnWorkActivityRestPercentage;
    }

    public void setReturnWorkActivityRestPercentage(Double returnWorkActivityRestPercentage) {
        this.returnWorkActivityRestPercentage = returnWorkActivityRestPercentage;
    }

    public Double getExerciseAdherencePercentage() {
        return exerciseAdherencePercentage;
    }

    public void setExerciseAdherencePercentage(Double exerciseAdherencePercentage) {
        this.exerciseAdherencePercentage = exerciseAdherencePercentage;
    }

    public Double getExerciseAdherenceRestPercentage() {
        return exerciseAdherenceRestPercentage;
    }

    public void setExerciseAdherenceRestPercentage(Double exerciseAdherenceRestPercentage) {
        this.exerciseAdherenceRestPercentage = exerciseAdherenceRestPercentage;
    }
}

