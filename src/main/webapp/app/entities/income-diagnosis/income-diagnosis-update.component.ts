import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IIncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';
import { IncomeDiagnosisService } from './income-diagnosis.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center';

@Component({
    selector: 'jhi-income-diagnosis-update',
    templateUrl: './income-diagnosis-update.component.html'
})
export class IncomeDiagnosisUpdateComponent implements OnInit {
    incomeDiagnosis: IIncomeDiagnosis;
    isSaving: boolean;

    rehabilitationcenters: IRehabilitationCenter[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected incomeDiagnosisService: IncomeDiagnosisService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ incomeDiagnosis }) => {
            this.incomeDiagnosis = incomeDiagnosis;
        });
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
        if (this.incomeDiagnosis.id !== undefined) {
            this.subscribeToSaveResponse(this.incomeDiagnosisService.update(this.incomeDiagnosis));
        } else {
            this.subscribeToSaveResponse(this.incomeDiagnosisService.create(this.incomeDiagnosis));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncomeDiagnosis>>) {
        result.subscribe((res: HttpResponse<IIncomeDiagnosis>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRehabilitationCenterById(index: number, item: IRehabilitationCenter) {
        return item.id;
    }
}
