import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatient } from 'app/shared/model/patient.model';
import { InitialAssessmentService } from 'app/entities/initial-assessment/initial-assessment.service';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { InitialAssessment } from 'app/shared/model/initial-assessment.model';
import { IIncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';
import { IComorbiditie } from 'app/shared/model/comorbiditie.model';
import { IncomeDiagnosisPatientService } from 'app/entities/income-diagnosis-patient/income-diagnosis-patient.service';
import { ComorbiditiesPatientService } from 'app/entities/comorbidities-patient/comorbidities-patient.service';
import { SessionService } from 'app/entities/session/session.service';
import { ISession } from 'app/shared/model/session.model';
import { FinalAssessmentService } from 'app/entities/final-assessment/final-assessment.service';
import { IFinalAssessment } from 'app/shared/model/final-assessment.model';

@Component({
    selector: 'jhi-patient-detail',
    templateUrl: './patient-detail.component.html'
})
export class PatientDetailComponent implements OnInit {
    patient: IPatient;
    initialAssesment;
    step = 4;
    incomeDiagnosis = [];
    comorbidities = [];
    sessions = [];
    finalAssessments = [];

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected initialAssessmentService: InitialAssessmentService,
        protected incomeDiagnosisPatientService: IncomeDiagnosisPatientService,
        protected comorbiditiesPatientService: ComorbiditiesPatientService,
        protected sessionService: SessionService,
        protected finalAssessmentService: FinalAssessmentService
    ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ patient }) => {
            this.patient = patient;
            this.initialAssessmentService
                .findByPatient(patient.id)
                .pipe(
                    filter((response: HttpResponse<InitialAssessment>) => response.ok),
                    map((initialAssessment: HttpResponse<InitialAssessment>) => initialAssessment.body)
                )
                .subscribe(initialAssesment => {
                    this.initialAssesment = initialAssesment;
                    this.loadDiagnosisPatient(initialAssesment.id);
                    this.loadComorbiditiesPatient(initialAssesment.id);
                    this.loadFinalAssessments(this.patient.id);
                    this.loadSessions(this.patient.id);
                });
        });
    }

    loadDiagnosisPatient(initialAssesmentId) {
        this.incomeDiagnosisPatientService
            .findByAssesment({ id: initialAssesmentId })
            .pipe(
                filter((mayBeOk: HttpResponse<IIncomeDiagnosis[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIncomeDiagnosis[]>) => response.body)
            )
            .subscribe((res: IIncomeDiagnosis[]) => (this.incomeDiagnosis = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    loadComorbiditiesPatient(initialAssesmentId) {
        this.comorbiditiesPatientService
            .findByAssesment({ id: initialAssesmentId })
            .pipe(
                filter((mayBeOk: HttpResponse<IComorbiditie[]>) => mayBeOk.ok),
                map((response: HttpResponse<IComorbiditie[]>) => response.body)
            )
            .subscribe((res: IComorbiditie[]) => (this.comorbidities = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    sort() {
        const result = ['executionDate' + ',' + 'asc'];
        result.push('id');
        return result;
    }

    loadSessions(patientId) {
        this.sessionService
            .queryByPatient({ patientId, sort: this.sort() })
            .pipe(
                filter((mayBeOk: HttpResponse<ISession[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISession[]>) => response.body)
            )
            .subscribe((res: ISession[]) => (this.sessions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    loadFinalAssessments(patientId) {
        this.finalAssessmentService
            .queryByPatient({ patientId })
            .pipe(
                filter((mayBeOk: HttpResponse<IFinalAssessment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFinalAssessment[]>) => response.body)
            )
            .subscribe((res: IFinalAssessment[]) => (this.finalAssessments = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    setStep(index: number) {
        this.step = index;
    }

    nextStep() {
        this.step++;
    }

    protected onError(errorMessage: string) {}

    prevStep() {
        this.step--;
    }

    previousState() {
        window.history.back();
    }
}
