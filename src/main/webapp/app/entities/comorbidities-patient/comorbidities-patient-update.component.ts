import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IComorbiditiesPatient } from 'app/shared/model/comorbidities-patient.model';
import { ComorbiditiesPatientService } from './comorbidities-patient.service';
import { IInitialAssessment } from 'app/shared/model/initial-assessment.model';
import { InitialAssessmentService } from 'app/entities/initial-assessment';

@Component({
    selector: 'jhi-comorbidities-patient-update',
    templateUrl: './comorbidities-patient-update.component.html'
})
export class ComorbiditiesPatientUpdateComponent implements OnInit {
    comorbiditiesPatient: IComorbiditiesPatient;
    isSaving: boolean;

    initialassessments: IInitialAssessment[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected comorbiditiesPatientService: ComorbiditiesPatientService,
        protected initialAssessmentService: InitialAssessmentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ comorbiditiesPatient }) => {
            this.comorbiditiesPatient = comorbiditiesPatient;
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
        if (this.comorbiditiesPatient.id !== undefined) {
            this.subscribeToSaveResponse(this.comorbiditiesPatientService.update(this.comorbiditiesPatient));
        } else {
            this.subscribeToSaveResponse(this.comorbiditiesPatientService.create(this.comorbiditiesPatient));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IComorbiditiesPatient>>) {
        result.subscribe(
            (res: HttpResponse<IComorbiditiesPatient>) => this.onSaveSuccess(),
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
