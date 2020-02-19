import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';
import { RehabilitationGroupService } from './rehabilitation-group.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center';

@Component({
    selector: 'jhi-rehabilitation-group-update',
    templateUrl: './rehabilitation-group-update.component.html'
})
export class RehabilitationGroupUpdateComponent implements OnInit {
    rehabilitationGroup: IRehabilitationGroup;
    isSaving: boolean;

    patients: IPatient[];

    rehabilitationcenters: IRehabilitationCenter[];
    creationDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rehabilitationGroupService: RehabilitationGroupService,
        protected patientService: PatientService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rehabilitationGroup }) => {
            this.rehabilitationGroup = rehabilitationGroup;
            this.creationDate =
                this.rehabilitationGroup.creationDate != null ? this.rehabilitationGroup.creationDate.format(DATE_TIME_FORMAT) : null;
        });
        this.patientService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPatient[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPatient[]>) => response.body)
            )
            .subscribe((res: IPatient[]) => (this.patients = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.rehabilitationCenterService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRehabilitationCenter[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRehabilitationCenter[]>) => response.body)
            )
            .subscribe(
                (res: IRehabilitationCenter[]) => (this.rehabilitationcenters = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.rehabilitationGroup.creationDate = this.creationDate != null ? moment(this.creationDate, DATE_TIME_FORMAT) : null;
        if (this.rehabilitationGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.rehabilitationGroupService.update(this.rehabilitationGroup));
        } else {
            this.subscribeToSaveResponse(this.rehabilitationGroupService.create(this.rehabilitationGroup));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRehabilitationGroup>>) {
        result.subscribe((res: HttpResponse<IRehabilitationGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRehabilitationCenterById(index: number, item: IRehabilitationCenter) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
