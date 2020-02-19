import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from './session.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient';

@Component({
    selector: 'jhi-session-update',
    templateUrl: './session-update.component.html'
})
export class SessionUpdateComponent implements OnInit {
    session: ISession;
    isSaving: boolean;

    patients: IPatient[];
    executionDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected sessionService: SessionService,
        protected patientService: PatientService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ session }) => {
            this.session = session;
            this.executionDate = this.session.executionDate != null ? this.session.executionDate.format(DATE_TIME_FORMAT) : null;
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
        this.session.executionDate = this.executionDate != null ? moment(this.executionDate, DATE_TIME_FORMAT) : null;
        if (this.session.id !== undefined) {
            this.subscribeToSaveResponse(this.sessionService.update(this.session));
        } else {
            this.subscribeToSaveResponse(this.sessionService.create(this.session));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISession>>) {
        result.subscribe((res: HttpResponse<ISession>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
