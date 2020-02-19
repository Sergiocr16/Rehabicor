import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IIncomeDiagnosisPatient } from 'app/shared/model/income-diagnosis-patient.model';
import { IncomeDiagnosisPatientService } from './income-diagnosis-patient.service';
import { IInitialAssessment } from 'app/shared/model/initial-assessment.model';
import { InitialAssessmentService } from 'app/entities/initial-assessment';

@Component({
    selector: 'jhi-income-diagnosis-patient-update',
    templateUrl: './income-diagnosis-patient-update.component.html'
})
export class IncomeDiagnosisPatientUpdateComponent implements OnInit {
    incomeDiagnosisPatient: IIncomeDiagnosisPatient;
    isSaving: boolean;

    initialassessments: IInitialAssessment[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected incomeDiagnosisPatientService: IncomeDiagnosisPatientService,
        protected initialAssessmentService: InitialAssessmentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ incomeDiagnosisPatient }) => {
            this.incomeDiagnosisPatient = incomeDiagnosisPatient;
        });
        this.initialAssessmentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInitialAssessment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInitialAssessment[]>) => response.body)
            )
            .subscribe(
                (res: IInitialAssessment[]) => (this.initialassessments = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.incomeDiagnosisPatient.id !== undefined) {
            this.subscribeToSaveResponse(this.incomeDiagnosisPatientService.update(this.incomeDiagnosisPatient));
        } else {
            this.subscribeToSaveResponse(this.incomeDiagnosisPatientService.create(this.incomeDiagnosisPatient));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncomeDiagnosisPatient>>) {
        result.subscribe(
            (res: HttpResponse<IIncomeDiagnosisPatient>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackInitialAssessmentById(index: number, item: IInitialAssessment) {
        return item.id;
    }
}
