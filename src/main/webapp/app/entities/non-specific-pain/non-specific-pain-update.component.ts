import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INonSpecificPain } from 'app/shared/model/non-specific-pain.model';
import { NonSpecificPainService } from './non-specific-pain.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center';

@Component({
    selector: 'jhi-non-specific-pain-update',
    templateUrl: './non-specific-pain-update.component.html'
})
export class NonSpecificPainUpdateComponent implements OnInit {
    nonSpecificPain: INonSpecificPain;
    isSaving: boolean;

    rehabilitationcenters: IRehabilitationCenter[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected nonSpecificPainService: NonSpecificPainService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ nonSpecificPain }) => {
            this.nonSpecificPain = nonSpecificPain;
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
        if (this.nonSpecificPain.id !== undefined) {
            this.subscribeToSaveResponse(this.nonSpecificPainService.update(this.nonSpecificPain));
        } else {
            this.subscribeToSaveResponse(this.nonSpecificPainService.create(this.nonSpecificPain));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<INonSpecificPain>>) {
        result.subscribe((res: HttpResponse<INonSpecificPain>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
