import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from './rehabilitation-center.service';

@Component({
    selector: 'jhi-rehabilitation-center-update',
    templateUrl: './rehabilitation-center-update.component.html'
})
export class RehabilitationCenterUpdateComponent implements OnInit {
    rehabilitationCenter: IRehabilitationCenter;
    isSaving: boolean;

    constructor(protected rehabilitationCenterService: RehabilitationCenterService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rehabilitationCenter }) => {
            this.rehabilitationCenter = rehabilitationCenter;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rehabilitationCenter.id !== undefined) {
            this.subscribeToSaveResponse(this.rehabilitationCenterService.update(this.rehabilitationCenter));
        } else {
            this.subscribeToSaveResponse(this.rehabilitationCenterService.create(this.rehabilitationCenter));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRehabilitationCenter>>) {
        result.subscribe(
            (res: HttpResponse<IRehabilitationCenter>) => this.onSaveSuccess(),
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
}
