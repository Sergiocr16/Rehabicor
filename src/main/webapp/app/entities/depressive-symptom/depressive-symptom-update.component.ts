import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDepressiveSymptom } from 'app/shared/model/depressive-symptom.model';
import { DepressiveSymptomService } from './depressive-symptom.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center';

@Component({
    selector: 'jhi-depressive-symptom-update',
    templateUrl: './depressive-symptom-update.component.html'
})
export class DepressiveSymptomUpdateComponent implements OnInit {
    depressiveSymptom: IDepressiveSymptom;
    isSaving: boolean;

    rehabilitationcenters: IRehabilitationCenter[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected depressiveSymptomService: DepressiveSymptomService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ depressiveSymptom }) => {
            this.depressiveSymptom = depressiveSymptom;
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
        if (this.depressiveSymptom.id !== undefined) {
            this.subscribeToSaveResponse(this.depressiveSymptomService.update(this.depressiveSymptom));
        } else {
            this.subscribeToSaveResponse(this.depressiveSymptomService.create(this.depressiveSymptom));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepressiveSymptom>>) {
        result.subscribe((res: HttpResponse<IDepressiveSymptom>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
