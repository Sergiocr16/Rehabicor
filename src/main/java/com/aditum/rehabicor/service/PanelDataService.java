package com.aditum.rehabicor.service;

import com.aditum.rehabicor.domain.Patient;
import com.aditum.rehabicor.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PanelDataService {

    private final Logger log = LoggerFactory.getLogger(PanelDataService.class);
    private PatientService patientService;

    private RehabilitationGroupService rehabilitationGroupService;

    private InitialAssessmentService initialAssessmentService;

    private FinalAssessmentService finalAssessmentService;

    private SessionService sessionService;

    private MinorEventsSessionService minorEventsSessionService;

    private MayorEventsSessionService mayorEventsSessionService;

    private MinorEventService minorEventService;

    private MayorEventService mayorEventService;

    private IncomeDiagnosisService incomeDiagnosisService;

    private ComorbiditieService comorbiditieService;

    private IncomeDiagnosisPatientService incomeDiagnosisPatientService;

    private ComorbiditiesPatientService comorbiditiesPatientService;

    public PanelDataService(PatientService patientService,
                            RehabilitationGroupService rehabilitationGroupService,
                            InitialAssessmentService initialAssessmentService,
                            FinalAssessmentService finalAssessmentService,
                            SessionService sessionService,
                            MinorEventsSessionService minorEventsSessionService,
                            MayorEventsSessionService mayorEventsSessionService,
                            MinorEventService minorEventService,
                            MayorEventService mayorEventService,
                            IncomeDiagnosisService incomeDiagnosisService,
                            ComorbiditieService comorbiditieService,
                            IncomeDiagnosisPatientService incomeDiagnosisPatientService,
                            ComorbiditiesPatientService comorbiditiesPatientService
    ) {
        this.patientService = patientService;
        this.rehabilitationGroupService = rehabilitationGroupService;
        this.initialAssessmentService = initialAssessmentService;
        this.finalAssessmentService = finalAssessmentService;
        this.sessionService = sessionService;
        this.minorEventsSessionService = minorEventsSessionService;
        this.mayorEventsSessionService = mayorEventsSessionService;
        this.minorEventService = minorEventService;
        this.mayorEventService = mayorEventService;
        this.comorbiditieService = comorbiditieService;
        this.incomeDiagnosisService = incomeDiagnosisService;
        this.incomeDiagnosisPatientService = incomeDiagnosisPatientService;
        this.comorbiditiesPatientService = comorbiditiesPatientService;
    }


    public List<MinorEventDTO> distributionMinorEventPerSessions(Long groupId, Long rehabilitationCenterId, Long minorEventId) {
        Set<PatientDTO> patients = rehabilitationGroupService.findOne(groupId).get().getPatients();
        List<MinorEventDTO> minorEventDTOS = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            MinorEventDTO minorEventDTO = new MinorEventDTO();
            minorEventDTO.setCode(i + "");
            minorEventDTO.setDescription(0 + "");
            minorEventDTOS.add(minorEventDTO);
        }
        for (PatientDTO patient : patients) {
            List<SessionDTO> patientSessions = this.sessionService.findAllByPatient(null, patient.getId()).getContent();
            for (int i = 0; i < patientSessions.size(); i++) {
                SessionDTO sessionDTO = patientSessions.get(i);
                sessionDTO.setMinorEventsSessions(setIdCeroMinor(this.minorEventsSessionService.findAllBySessionId(null, sessionDTO.getId()).getContent()));
                for (MinorEventsSessionDTO minorEventsSessionDTO : sessionDTO.getMinorEventsSessions()) {
                    if (minorEventsSessionDTO.isExist()) {
                        if (minorEventsSessionDTO.getMinorEventRelation() == minorEventId) {
                            minorEventDTOS.get(i).setDescription((Integer.parseInt(minorEventDTOS.get(i).getDescription()) + 1) + "");
//                            MinorEventsSessionDTO exist = this.existEventMinor(minorEvents, minorEventsSessionDTO);
//                            if (exist != null) { // <- look for item!
//                                minorEvents.add(minorEventsSessionDTO);
//                            }
                        }
                    }
                }
            }
        }
        return minorEventDTOS;
    }

    public List<MayorEventDTO> distributionMayorEventPerSessions(Long groupId, Long rehabilitationCenterId, Long mayorEventId) {
        Set<PatientDTO> patients = rehabilitationGroupService.findOne(groupId).get().getPatients();
        List<MayorEventDTO> mayorEventDTOS = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            MayorEventDTO mayorEventDTO = new MayorEventDTO();
            mayorEventDTO.setCode(i + "");
            mayorEventDTO.setDescription(0 + "");
            mayorEventDTOS.add(mayorEventDTO);
        }
        for (PatientDTO patient : patients) {
            List<SessionDTO> patientSessions = this.sessionService.findAllByPatient(null, patient.getId()).getContent();
            List<MayorEventsSessionDTO> mayorEvents = new ArrayList<>();
            for (int i = 0; i < patientSessions.size(); i++) {
                SessionDTO sessionDTO = patientSessions.get(i);
                sessionDTO.setMayorEventsSessions(this.mayorEventsSessionService.findAllBySession(null, sessionDTO.getId()).getContent());
                for (MayorEventsSessionDTO mayorEventsSessionDTO : sessionDTO.getMayorEventsSessions()) {
                    if (mayorEventsSessionDTO.isExist()) {
                        if (mayorEventsSessionDTO.getMayorEventRelation() == mayorEventId) {
                            mayorEventDTOS.get(i).setDescription((Integer.parseInt(mayorEventDTOS.get(i).getDescription()) + 1) + "");
                        }
                    }
                }
            }
        }
        return mayorEventDTOS;
    }

    public PanelDataDTO calculatePanelDataPerGroup(RehabilitationGroupDTO rehabilitationGroup) {
        PanelDataDTO panelDataDTO = new PanelDataDTO();
        Set<PatientDTO> patients = rehabilitationGroup.getPatients();
        Integer patientsTotal = patients.size();
        Integer abandonmentNonMedicalCauseTotal = 0;
        Integer assessmentIndividualGroupSupportTotal = 0;
        Integer improvementFunctionalCapacityTotal = 0;
        Integer improvementLipidicProfileTotal = 0;
        Integer improvementGlycemicControlTotal = 0;
        Integer weightReductionTotal = 0;
        Integer suspendedSmokingTotal = 0;
        Integer returnWorkActivityTotal = 0;

//        ?????
        Integer exerciseAdherenceTotal = 0;
        Integer followUpExternalTotal = 0;

        panelDataDTO.setDistributionMinorEvents(this.distributionMinorEvents(rehabilitationGroup.getRehabilitationCenterId(), patients));
        for (PatientDTO patient : patients) {
            List<FinalAssessmentDTO> finalAssessments = this.finalAssessmentService.findAllByPatient(null, patient.getId()).getContent();
            if (finalAssessments.size() > 0) {
                FinalAssessmentDTO finalAssessment = finalAssessments.get(finalAssessments.size() == 2 ? 1 : 0);
                InitialAssessmentDTO initialAssessment = this.initialAssessmentService.findOneByPatient(patient.getId()).get();
                abandonmentNonMedicalCauseTotal += this.abandonmentNonMedicalCausePercentage(patient, initialAssessment, finalAssessment);
                assessmentIndividualGroupSupportTotal += this.assessmentIndividualGroupSupportPercentage(patient);
                improvementFunctionalCapacityTotal += this.improvementFunctionalCapacityPercentage(patient, initialAssessment, finalAssessment);
                improvementLipidicProfileTotal += this.improvementLipidicProfilePercentage(patient, initialAssessment, finalAssessment);
                improvementGlycemicControlTotal += this.improvementGlycemicControlPercentage(patient, initialAssessment, finalAssessment);
                weightReductionTotal += this.weightReductionPercentage(patient, initialAssessment, finalAssessment);
                suspendedSmokingTotal += this.suspendedSmokingPercentage(patient, initialAssessment, finalAssessment);
                returnWorkActivityTotal += this.returnWorkActivityPercentage(patient, initialAssessment, finalAssessment);
                exerciseAdherenceTotal += this.exerciseAdherencePercentage(patient, initialAssessment, finalAssessment);
            }
        }

        panelDataDTO.setPatientsTotal(patientsTotal);

        panelDataDTO.setAbandonmentNonMedicalCausePercentage(calculatePercentaje(patientsTotal, abandonmentNonMedicalCauseTotal));
        panelDataDTO.setAbandonmentNonMedicalCauseRestPercentage(calculatePercentajeRest(patientsTotal, abandonmentNonMedicalCauseTotal));

        panelDataDTO.setAssessmentIndividualGroupSupportPercentage(calculatePercentaje(patientsTotal, assessmentIndividualGroupSupportTotal));
        panelDataDTO.setAssessmentIndividualGroupSupportRestPercentage(calculatePercentajeRest(patientsTotal, assessmentIndividualGroupSupportTotal));

        panelDataDTO.setImprovementFunctionalCapacityPercentage(calculatePercentaje(patientsTotal, improvementFunctionalCapacityTotal));
        panelDataDTO.setImprovementFunctionalCapacityRestPercentage(calculatePercentajeRest(patientsTotal, improvementFunctionalCapacityTotal));

        panelDataDTO.setImprovementLipidicProfilePercentage(calculatePercentaje(patientsTotal, improvementLipidicProfileTotal));
        panelDataDTO.setImprovementLipidicProfileRestPercentage(calculatePercentajeRest(patientsTotal, improvementLipidicProfileTotal));

        panelDataDTO.setImprovementGlycemicControlPercentage(calculatePercentaje(patientsTotal, improvementGlycemicControlTotal));
        panelDataDTO.setImprovementGlycemicControlPercentage(calculatePercentaje(patientsTotal, improvementGlycemicControlTotal));

        panelDataDTO.setWeightReductionPercentage(calculatePercentaje(patientsTotal, weightReductionTotal));
        panelDataDTO.setWeightReductionRestPercentage(calculatePercentajeRest(patientsTotal, weightReductionTotal));

        panelDataDTO.setSuspendedSmokingPercentage(calculatePercentaje(patientsTotal, suspendedSmokingTotal));
        panelDataDTO.setSuspendedSmokingRestPercentage(calculatePercentajeRest(patientsTotal, suspendedSmokingTotal));

        panelDataDTO.setReturnWorkActivityPercentage(calculatePercentaje(patientsTotal, returnWorkActivityTotal));
        panelDataDTO.setReturnWorkActivityRestPercentage(calculatePercentajeRest(patientsTotal, returnWorkActivityTotal));

        return panelDataDTO;
    }

    private double calculatePercentaje(int total, int number) {
        double percentage = ((number * 100) / total);
        return percentage;
    }

    private double calculatePercentajeRest(int total, int number) {
        int rest = total - number;
        double percentage = (rest * 100) / total;
        return percentage;
    }


    private void fullSelectionCriteriaPercentage(Patient patient) {

    }

    private Integer assessmentIndividualGroupSupportPercentage(PatientDTO patient) {
        if (patient.getRehabStatus() >= 1) {
            List<SessionDTO> sessions = this.sessionService.findAllByPatient(null, patient.getId()).getContent();
            if (sessions.size() >= 2) {
                return 1;
            } else {
                return 0;
            }
        }
        return 0;
    }

    private Integer abandonmentNonMedicalCausePercentage(PatientDTO patient, InitialAssessmentDTO initialAssessment, FinalAssessmentDTO finalAssessment) {
        if (patient.getRehabStatus() >= 2) {
            if (finalAssessment.isAbandonmentMedicCause()) {
                return 1;
            } else {
                return 0;
            }
        }
        return 0;
    }

    private Integer improvementFunctionalCapacityPercentage(PatientDTO patient, InitialAssessmentDTO initialAssessment, FinalAssessmentDTO finalAssessment) {
        if (patient.getRehabStatus() >= 2) {
            return improve(initialAssessment.getBaselineFunctionalCapacity(), finalAssessment.getBaselineFunctionalCapacity());
        }
        return 0;
    }

    private Integer improvementLipidicProfilePercentage(PatientDTO patient, InitialAssessmentDTO initialAssessment, FinalAssessmentDTO finalAssessment) {
        if (patient.getRehabStatus() >= 2) {
            return improve(initialAssessment.gethDL(), finalAssessment.gethDL()) + improve(finalAssessment.getlDL(), initialAssessment.getlDL()) == 2 ? 1 : 0;
        }
        return 0;
    }

    private Integer improvementGlycemicControlPercentage(PatientDTO patient, InitialAssessmentDTO initialAssessment, FinalAssessmentDTO finalAssessment) {
        if (patient.getRehabStatus() >= 2) {
            return improve(finalAssessment.getHbiac(), initialAssessment.getHbiac());
        }
        return 0;
    }

    private Integer weightReductionPercentage(PatientDTO patient, InitialAssessmentDTO initialAssessment, FinalAssessmentDTO finalAssessment) {
        if (patient.getRehabStatus() >= 2) {
            double fivePercent = Double.parseDouble(initialAssessment.getWeight()) * 0.05;
            double differenceWeight = Double.parseDouble(initialAssessment.getWeight()) - Double.parseDouble(finalAssessment.getWeight());
            if (differenceWeight < 0) {
                differenceWeight = differenceWeight + differenceWeight;
            }
            if (differenceWeight >= fivePercent) {
                return 1;
            }
        }
        return 0;
    }

    private Integer suspendedSmokingPercentage(PatientDTO patient, InitialAssessmentDTO initialAssessment, FinalAssessmentDTO finalAssessment) {
        if (patient.getRehabStatus() >= 2) {
            Boolean smokingBefore = initialAssessment.getSmoking() == "Activo";
            Boolean smokingAfter = finalAssessment.getSmoking() == "Activo";
            if (smokingBefore && !smokingAfter) {
                return 1;
            }
        }
        return 0;
    }

    private Integer returnWorkActivityPercentage(PatientDTO patient, InitialAssessmentDTO initialAssessment, FinalAssessmentDTO finalAssessment) {
        if (patient.getRehabStatus() >= 2) {
            if (patient.getOcupation() != "Asalariado") {
                return finalAssessment.isIsWorking() ? 1 : 0;
            }
        }
        return 0;
    }

    private Integer exerciseAdherencePercentage(PatientDTO patient, InitialAssessmentDTO initialAssessment, FinalAssessmentDTO finalAssessment) {
        if (patient.getRehabStatus() >= 2) {
            return finalAssessment.isAbandonment() ? 1 : 0;
        }
        return 0;
    }

    private Integer improve(String value1, String value2) {
        return Double.parseDouble(value1) < Double.parseDouble(value2) ? 1 : 0;
    }

    private List<SesionDistributionDTO> distributionMinorEvents(Long rehabilitationCenterId, Set<PatientDTO> patients) {
        List<SesionDistributionDTO> sesionDistribution = new ArrayList<>();

//        if (MiPedidoActivity.itemsListVMiPedido.contains(item)) { // <- look for item!
////            // ... item already in list
////        } else {
////            MiPedidoActivity.itemsListVMiPedido.add(item);
////        }
        for (int i = 0; i < 60; i++) {
            SesionDistributionDTO sesionDistributionDTO = new SesionDistributionDTO();
            sesionDistributionDTO.setSesionNumber(i + "");
            sesionDistributionDTO.setMayorEventsPerSesion(0);
            sesionDistributionDTO.setMinorEventsPerSesion(0);
            sesionDistributionDTO.setMinorEvents(this.toMinorSessionDTO(this.minorEventService.findAll(null, rehabilitationCenterId).getContent()));
            sesionDistributionDTO.setMayorEvents(this.toMayorSessionDTO(this.mayorEventService.findAll(null, rehabilitationCenterId).getContent()));
            sesionDistribution.add(sesionDistributionDTO);
        }
        for (PatientDTO patient : patients) {
            List<SessionDTO> patientSessions = this.sessionService.findAllByPatient(null, patient.getId()).getContent();
            List<MinorEventsSessionDTO> minorEvents = new ArrayList<>();
            List<MayorEventsSessionDTO> mayorEvents = new ArrayList<>();
            for (int i = 0; i < patientSessions.size(); i++) {
                SessionDTO sessionDTO = patientSessions.get(i);
                sessionDTO.setMinorEventsSessions(setIdCeroMinor(this.minorEventsSessionService.findAllBySessionId(null, sessionDTO.getId()).getContent()));
                for (MinorEventsSessionDTO minorEventsSessionDTO : sessionDTO.getMinorEventsSessions()) {
                    if (minorEventsSessionDTO.isExist()) {
                        sesionDistribution.get(i).setMinorEventsPerSesion(sesionDistribution.get(i).getMinorEventsPerSesion() + 1);
                        MinorEventsSessionDTO exist = this.existEventMinor(minorEvents, minorEventsSessionDTO);
                        if (exist != null) { // <- look for item!
                            minorEvents.add(minorEventsSessionDTO);
                        }
                    }
                }
                sesionDistribution.get(i).setMinorEvents(this.filterSessionListMinor(sesionDistribution.get(i).getMinorEvents(), minorEvents));
//              MAYOR
                sessionDTO.setMayorEventsSessions(setIdCeroMayor(this.mayorEventsSessionService.findAllBySession(null, sessionDTO.getId()).getContent()));
                for (MayorEventsSessionDTO mayorEventsSessionDTO : sessionDTO.getMayorEventsSessions()) {
                    if (mayorEventsSessionDTO.isExist()) {
                        sesionDistribution.get(i).setMayorEventsPerSesion(sesionDistribution.get(i).getMayorEventsPerSesion() + 1);
                        MayorEventsSessionDTO exist = this.existEventMayor(mayorEvents, mayorEventsSessionDTO);
                        if (exist != null) { // <- look for item!
                            mayorEvents.add(mayorEventsSessionDTO);
                        }
                    }
                }
                sesionDistribution.get(i).setMayorEvents(this.filterSessionListMayor(sesionDistribution.get(i).getMayorEvents(), mayorEvents));
            }
        }
        return sesionDistribution;
    }


    private List<MinorEventsSessionDTO> toMinorSessionDTO(List<MinorEventDTO> minorEvents) {
        List<MinorEventsSessionDTO> minorEventsSessionDTOS = new ArrayList<>();
        for (MinorEventDTO minorEventDTO : minorEvents) {
            MinorEventsSessionDTO minorEventsSessionDTO = new MinorEventsSessionDTO();
            minorEventsSessionDTO.setId((long) 0);
            minorEventsSessionDTO.setDescription(minorEventDTO.getDescription());
            minorEventsSessionDTOS.add(minorEventsSessionDTO);
        }
        return minorEventsSessionDTOS;
    }

    private List<MayorEventsSessionDTO> toMayorSessionDTO(List<MayorEventDTO> mayorEvents) {
        List<MayorEventsSessionDTO> mayorEventsSessionDTOS = new ArrayList<>();
        for (MayorEventDTO mayorEventDTO : mayorEvents) {
            MayorEventsSessionDTO mayorEventsSessionDTO = new MayorEventsSessionDTO();
            mayorEventsSessionDTO.setId((long) 0);
            mayorEventsSessionDTO.setDescription(mayorEventDTO.getDescription());
            mayorEventsSessionDTOS.add(mayorEventsSessionDTO);
        }
        return mayorEventsSessionDTOS;
    }

    private List<MinorEventsSessionDTO> setIdCeroMinor(List<MinorEventsSessionDTO> minorEventsSessionDTOS) {
        for (MinorEventsSessionDTO minorEventsSession : minorEventsSessionDTOS) {
            minorEventsSession.setId((long) 0);
            minorEventsSession.setSessionId((long) 0);
        }
        return minorEventsSessionDTOS;
    }

    private List<MayorEventsSessionDTO> setIdCeroMayor(List<MayorEventsSessionDTO> mayorEventsSessionDTOs) {
        for (MayorEventsSessionDTO mayorEventsSession : mayorEventsSessionDTOs) {
            mayorEventsSession.setId((long) 0);
            mayorEventsSession.setSessionId((long) 0);
        }
        return mayorEventsSessionDTOs;
    }

    private List<MinorEventsSessionDTO> filterSessionListMinor(List<MinorEventsSessionDTO> minorSessionListGlobal, List<MinorEventsSessionDTO> minorSessionList) {
        List<MinorEventsSessionDTO> minorSessionListGlobalFiltered = new ArrayList<>();
        for (MinorEventsSessionDTO minorEventsSessionGlobal : minorSessionListGlobal) {
            for (MinorEventsSessionDTO minorEventsSessionSession : minorSessionList) {
                if (minorEventsSessionGlobal.getDescription().equals(minorEventsSessionSession.getDescription())) {
                    minorEventsSessionGlobal.setId(minorEventsSessionGlobal.getId() + 1);
                }
            }
        }
        for (MinorEventsSessionDTO minorEventsSessionGlobal : minorSessionListGlobal) {
            if (minorEventsSessionGlobal.getId() > 0) {
                minorSessionListGlobalFiltered.add(minorEventsSessionGlobal);
            }
        }
        return minorSessionListGlobalFiltered;
    }

    private List<MayorEventsSessionDTO> filterSessionListMayor(List<MayorEventsSessionDTO> mayorSessionListGlobal, List<MayorEventsSessionDTO> mayorSessionList) {
        List<MayorEventsSessionDTO> mayorSessionListGlobalFiltered = new ArrayList<>();
        for (MayorEventsSessionDTO mayorEventsSessionGlobal : mayorSessionListGlobal) {
            for (MayorEventsSessionDTO mayorEventsSessionSession : mayorSessionList) {
                if (mayorEventsSessionGlobal.getDescription().equals(mayorEventsSessionSession.getDescription())) {
                    mayorEventsSessionGlobal.setId(mayorEventsSessionGlobal.getId() + 1);
                }
            }
        }
        for (MayorEventsSessionDTO minorEventsSessionGlobal : mayorSessionListGlobal) {
            if (minorEventsSessionGlobal.getId() > 0) {
                mayorSessionListGlobalFiltered.add(minorEventsSessionGlobal);
            }
        }
        return mayorSessionListGlobalFiltered;
    }

    private MinorEventsSessionDTO existEventMinor(List<MinorEventsSessionDTO> all, MinorEventsSessionDTO minorPerSession) {
        int exist = 0;
        if (all.size() == 0) {
            minorPerSession.setId(minorPerSession.getId() + 1);
            return minorPerSession;
        }
        MinorEventsSessionDTO existing = new MinorEventsSessionDTO();
        for (MinorEventsSessionDTO minorEventsSessionDTO : all) {
            if (minorEventsSessionDTO.getDescription().equals(minorPerSession.getDescription())) {
                existing = minorEventsSessionDTO;
                exist++;
            }
        }
        if (exist > 0) {
            existing.setId(existing.getId() + 1);
            return null;
        } else {
            minorPerSession.setId(minorPerSession.getId() + 1);
            return minorPerSession;
        }
    }

    private MayorEventsSessionDTO existEventMayor(List<MayorEventsSessionDTO> all, MayorEventsSessionDTO mayorPerSession) {
        int exist = 0;
        if (all.size() == 0) {
            mayorPerSession.setId(mayorPerSession.getId() + 1);
            return mayorPerSession;
        }
        for (MayorEventsSessionDTO minorEventsSessionDTO : all) {
            if (minorEventsSessionDTO.getDescription().equals(mayorPerSession.getDescription())) {
                exist++;
            }
        }
        if (exist > 0) {
            mayorPerSession.setId(mayorPerSession.getId() + 1);
            return mayorPerSession;
        } else {
            return null;
        }
    }

    private List<SesionDistributionDTO> distributionMayorEvents() {
        List<SesionDistributionDTO> sesionDistribution = new ArrayList<>();
        return sesionDistribution;
    }


    public GroupCharacteristicsDTO groupCharacteristics(RehabilitationGroupDTO rehabilitationGroup) {
        GroupCharacteristicsDTO groupCharacteristics = new GroupCharacteristicsDTO();

        List<IncomeDiagnosisDTO> incomeDiagnosisList = this.incomeDiagnosisService.findAllNoPage(rehabilitationGroup.getId());
        for (int i = 0; i < incomeDiagnosisList.size(); i++) {
            IncomeDiagnosisDTO incomeDiagnosisDTO = incomeDiagnosisList.get(i);
            incomeDiagnosisDTO.setRehabilitationCenterId(Long.parseLong(0 + ""));
            groupCharacteristics.getIncomeDiagnosisDistribution().add(incomeDiagnosisDTO);
        }

        List<ComorbiditieDTO> comorbiditieList = this.comorbiditieService.findAllNoPage(rehabilitationGroup.getId());
        for (int i = 0; i < comorbiditieList.size(); i++) {
            ComorbiditieDTO comorbiditieDTO = comorbiditieList.get(i);
            comorbiditieDTO.setRehabilitationCenterId(Long.parseLong(0 + ""));
            groupCharacteristics.getComorbiditieDistribution().add(comorbiditieDTO);
        }

        Set<PatientDTO> patients = rehabilitationGroup.getPatients();
        int ageTotal = 0;
        int lastEventOcurredTotal = 0;
        int tabaquismTotal = 0;
        double hbiacTotal = 0;
        double functionalCapacityTotal = 0;

        for (PatientDTO patient : patients) {
            InitialAssessmentDTO initialAssessment = this.initialAssessmentService.findOneByPatient(patient.getId()).get();
            distributionGenderPatient(patient, groupCharacteristics);
            distributionScholarity(patient, groupCharacteristics);
            distributionCardiovascularRisk(initialAssessment, groupCharacteristics);
            distributionIMC(initialAssessment, groupCharacteristics);
            distributionIncomeDiagnosisPatient(initialAssessment, incomeDiagnosisList, groupCharacteristics);
            distributionComorbiditiesPatient(initialAssessment, comorbiditieList, groupCharacteristics);
//            totals
            ageTotal += patient.getAge();
            lastEventOcurredTotal += patient.getLastEventOcurred();
            tabaquismTotal += initialAssessment.getSmoking().equals("Activo") ? 1 : 0;
            hbiacTotal += Double.parseDouble(initialAssessment.getHbiac());
            functionalCapacityTotal += Double.parseDouble(initialAssessment.getBaselineFunctionalCapacity());
        }

        groupCharacteristics.setAverageAge(ageTotal / patients.size());
        groupCharacteristics.setAverageLastEvent(lastEventOcurredTotal / patients.size());
        groupCharacteristics.setTabaquismPercentaje((tabaquismTotal * 100) / patients.size());
        groupCharacteristics.setAverageHb1Ac(hbiacTotal / patients.size());
        groupCharacteristics.setAverageFunctionalCapacity(functionalCapacityTotal / patients.size());

        groupCharacteristics.setRehabilitationGroupDTO(rehabilitationGroup);
        return groupCharacteristics;
    }

    private void distributionGenderPatient(PatientDTO patientDTO, GroupCharacteristicsDTO groupCharacteristics) {
        if (patientDTO.getSex().equals("Masculino")) {
            groupCharacteristics.increaseSex(0);
        } else {
            groupCharacteristics.increaseSex(1);
        }
    }

    private void distributionIncomeDiagnosisPatient(InitialAssessmentDTO initialAssessmentDTO, List<IncomeDiagnosisDTO> incomeDiagnosisList, GroupCharacteristicsDTO groupCharacteristics) {
        List<IncomeDiagnosisPatientDTO> incomeDiagnosisPatientDTOS = this.incomeDiagnosisPatientService.findAllByInitialAsessment(initialAssessmentDTO.getId());
        for (IncomeDiagnosisDTO incomeDiagnosis : incomeDiagnosisList) {
            for (IncomeDiagnosisPatientDTO incomeDiagnosisPatient : incomeDiagnosisPatientDTOS) {
                if (incomeDiagnosis.getId() == incomeDiagnosisPatient.getIncomeDiagnosisRelation()) {
                    if (incomeDiagnosisPatient.isExist()) {
                        incomeDiagnosis.setRehabilitationCenterId(incomeDiagnosis.getRehabilitationCenterId() + 1);
                    }
                }
            }
        }
    }

    private void distributionComorbiditiesPatient(InitialAssessmentDTO initialAssessmentDTO, List<ComorbiditieDTO> comorbiditieDTOList, GroupCharacteristicsDTO groupCharacteristics) {
        List<ComorbiditiesPatientDTO> comorbiditiesPatientDTOS = this.comorbiditiesPatientService.findAllByInitialAsessment(initialAssessmentDTO.getId());
        for (ComorbiditieDTO comorbiditieDTO : comorbiditieDTOList) {
            for (ComorbiditiesPatientDTO comorbiditiesPatientDTO : comorbiditiesPatientDTOS) {
                if (comorbiditieDTO.getId() == comorbiditiesPatientDTO.getComorbiditieRelation()) {
                    if (comorbiditiesPatientDTO.isExist()) {
                        comorbiditieDTO.setRehabilitationCenterId(comorbiditieDTO.getRehabilitationCenterId() + 1);
                    }
                }
            }
        }
    }


    private void distributionScholarity(PatientDTO patientDTO, GroupCharacteristicsDTO groupCharacteristics) {
//            '0 - Ninguna',
//            '1 - Primaria Incompleta',
//            '2 - Primaria completa',
//            '3 - Secundaria Incompleta',
//            '4 - Secundaria completa',
//            '5 - Universitaria Incompleta',
//            '6 - Universitaria completa',
//            '7 - Parauniversitaria'
        switch (patientDTO.getScholarship()) {
            case "Ninguna":
                groupCharacteristics.increaseScholarity(0);
                break;
            case "Primaria Incompleta":
                groupCharacteristics.increaseScholarity(1);
                break;
            case "Primaria completa":
                groupCharacteristics.increaseScholarity(2);
                break;
            case "Secundaria Incompleta":
                groupCharacteristics.increaseScholarity(3);
                break;
            case "Secundaria completa":
                groupCharacteristics.increaseScholarity(4);
                break;
            case "Universitaria Incompleta":
                groupCharacteristics.increaseScholarity(5);
                break;
            case "Universitaria completa":
                groupCharacteristics.increaseScholarity(6);
                break;
            case "Parauniversitaria":
                groupCharacteristics.increaseScholarity(7);
                break;
        }
    }

    private void distributionCardiovascularRisk(InitialAssessmentDTO initialAssessment, GroupCharacteristicsDTO groupCharacteristics) {
//        '0 - Alto', '1 - Moderado', '2 - Bajo'
        switch (initialAssessment.getCardiovascularRisk()) {
            case "Alto":
                groupCharacteristics.increaseCardiovascularRisk(0);
                break;
            case "Moderado":
                groupCharacteristics.increaseCardiovascularRisk(1);
                break;
            case "Bajo":
                groupCharacteristics.increaseCardiovascularRisk(2);
                break;
        }
    }

    private void distributionIMC(InitialAssessmentDTO initialAssessment, GroupCharacteristicsDTO groupCharacteristics) {
//     -Promedio de IMC  en rangos de [0](-19)delgadez  [1](20-25)normal  [2](26-30)sobrepeso [3](30 o mas) obesidad

        double imc = Double.parseDouble(initialAssessment.getiMC());
        if (imc <= 19) {
            groupCharacteristics.increaseIMC(0);
        }
        if (imc >= 20 && imc <= 25) {
            groupCharacteristics.increaseIMC(1);

        }
        if (imc >= 26 && imc <= 30) {
            groupCharacteristics.increaseIMC(2);

        }
        if (imc >= 30) {
            groupCharacteristics.increaseIMC(3);

        }
    }
}
