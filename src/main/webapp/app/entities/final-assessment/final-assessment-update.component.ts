import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IFinalAssessment } from 'app/shared/model/final-assessment.model';
import { FinalAssessmentService } from './final-assessment.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient';

@Component({
    selector: 'jhi-final-assessment-update',
    templateUrl: './final-assessment-update.component.html'
})
export class FinalAssessmentUpdateComponent implements OnInit {
    finalAssessment: IFinalAssessment;
    isSaving: boolean;

    patients: IPatient[];
    executionDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected finalAssessmentService: FinalAssessmentService,
        protected patientService: PatientService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ finalAssessment }) => {
            this.finalAssessment = finalAssessment;
            this.executionDate =
                this.finalAssessment.executionDate != null ? this.finalAssessment.executionDate.format(DATE_TIME_FORMAT) : null;
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
        this.finalAssessment.executionDate = this.executionDate != null ? moment(this.executionDate, DATE_TIME_FORMAT) : null;
        if (this.finalAssessment.id !== undefined) {
            this.subscribeToSaveResponse(this.finalAssessmentService.update(this.finalAssessment));
        } else {
            this.subscribeToSaveResponse(this.finalAssessmentService.create(this.finalAssessment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFinalAssessment>>) {
        result.subscribe((res: HttpResponse<IFinalAssessment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
