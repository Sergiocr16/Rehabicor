import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInitialAssessment } from 'app/shared/model/initial-assessment.model';
import { InitialAssessmentService } from './initial-assessment.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient';

@Component({
    selector: 'jhi-initial-assessment-update',
    templateUrl: './initial-assessment-update.component.html'
})
export class InitialAssessmentUpdateComponent implements OnInit {
    initialAssessment: IInitialAssessment;
    isSaving: boolean;

    patients: IPatient[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected initialAssessmentService: InitialAssessmentService,
        protected patientService: PatientService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ initialAssessment }) => {
            this.initialAssessment = initialAssessment;
        });
        this.patientService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPatient[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPatient[]>) => response.body)
            )
            .subscribe((res: IPatient[]) => (this.patients = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.initialAssessment.id !== undefined) {
            this.subscribeToSaveResponse(this.initialAssessmentService.update(this.initialAssessment));
        } else {
            this.subscribeToSaveResponse(this.initialAssessmentService.create(this.initialAssessment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInitialAssessment>>) {
        result.subscribe((res: HttpResponse<IInitialAssessment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPatientById(index: number, item: IPatient) {
        return item.id;
    }
}
