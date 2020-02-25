import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'app-user',
                loadChildren: './app-user/app-user.module#RehabicorAppUserModule'
            },
            {
                path: 'comorbiditie',
                loadChildren: './comorbiditie/comorbiditie.module#RehabicorComorbiditieModule'
            },
            {
                path: 'comorbidities-patient',
                loadChildren: './comorbidities-patient/comorbidities-patient.module#RehabicorComorbiditiesPatientModule'
            },
            {
                path: 'depressive-symptom',
                loadChildren: './depressive-symptom/depressive-symptom.module#RehabicorDepressiveSymptomModule'
            },
            {
                path: 'depressive-symptoms-session',
                loadChildren: './depressive-symptoms-session/depressive-symptoms-session.module#RehabicorDepressiveSymptomsSessionModule'
            },
            {
                path: 'final-assessment',
                loadChildren: './final-assessment/final-assessment.module#RehabicorFinalAssessmentModule'
            },
            {
                path: 'income-diagnosis',
                loadChildren: './income-diagnosis/income-diagnosis.module#RehabicorIncomeDiagnosisModule'
            },
            {
                path: 'income-diagnosis-patient',
                loadChildren: './income-diagnosis-patient/income-diagnosis-patient.module#RehabicorIncomeDiagnosisPatientModule'
            },
            {
                path: 'initial-assessment',
                loadChildren: './initial-assessment/initial-assessment.module#RehabicorInitialAssessmentModule'
            },
            {
                path: 'mayor-event',
                loadChildren: './mayor-event/mayor-event.module#RehabicorMayorEventModule'
            },
            {
                path: 'mayor-events-session',
                loadChildren: './mayor-events-session/mayor-events-session.module#RehabicorMayorEventsSessionModule'
            },
            {
                path: 'minor-event',
                loadChildren: './minor-event/minor-event.module#RehabicorMinorEventModule'
            },
            {
                path: 'minor-events-session',
                loadChildren: './minor-events-session/minor-events-session.module#RehabicorMinorEventsSessionModule'
            },
            {
                path: 'non-specific-pain',
                loadChildren: './non-specific-pain/non-specific-pain.module#RehabicorNonSpecificPainModule'
            },
            {
                path: 'non-specific-pains-session',
                loadChildren: './non-specific-pains-session/non-specific-pains-session.module#RehabicorNonSpecificPainsSessionModule'
            },
            {
                path: 'patient',
                loadChildren: './patient/patient.module#RehabicorPatientModule'
            },
            {
                path: 'rehabilitation-center',
                loadChildren: './rehabilitation-center/rehabilitation-center.module#RehabicorRehabilitationCenterModule'
            },
            {
                path: 'rehabilitation-group',
                loadChildren: './rehabilitation-group/rehabilitation-group.module#RehabicorRehabilitationGroupModule'
            },
            {
                path: 'evaluation',
                loadChildren: './evaluation/rehabilitation-group-evaluation.module#RehabicorRehabilitationGroupModule'
            },
            {
                path: 'panel-data',
                loadChildren: './panel-data/rehabilitation-group-panel-data.module#RehabicorRehabilitationGroupModule'
            },
            {
                path: 'session',
                loadChildren: './session/session.module#RehabicorSessionModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorEntityModule {}
