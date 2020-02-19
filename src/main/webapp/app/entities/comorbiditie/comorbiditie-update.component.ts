import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IComorbiditie } from 'app/shared/model/comorbiditie.model';
import { ComorbiditieService } from './comorbiditie.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center';

@Component({
    selector: 'jhi-comorbiditie-update',
    templateUrl: './comorbiditie-update.component.html'
})
export class ComorbiditieUpdateComponent implements OnInit {
    comorbiditie: IComorbiditie;
    isSaving: boolean;

    rehabilitationcenters: IRehabilitationCenter[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected comorbiditieService: ComorbiditieService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ comorbiditie }) => {
            this.comorbiditie = comorbiditie;
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
        if (this.comorbiditie.id !== undefined) {
            this.subscribeToSaveResponse(this.comorbiditieService.update(this.comorbiditie));
        } else {
            this.subscribeToSaveResponse(this.comorbiditieService.create(this.comorbiditie));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IComorbiditie>>) {
        result.subscribe((res: HttpResponse<IComorbiditie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
