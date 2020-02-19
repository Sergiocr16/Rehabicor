import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMayorEventsSession } from 'app/shared/model/mayor-events-session.model';
import { MayorEventsSessionService } from './mayor-events-session.service';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session';

@Component({
    selector: 'jhi-mayor-events-session-update',
    templateUrl: './mayor-events-session-update.component.html'
})
export class MayorEventsSessionUpdateComponent implements OnInit {
    mayorEventsSession: IMayorEventsSession;
    isSaving: boolean;

    sessions: ISession[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected mayorEventsSessionService: MayorEventsSessionService,
        protected sessionService: SessionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mayorEventsSession }) => {
            this.mayorEventsSession = mayorEventsSession;
        });
        this.sessionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISession[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISession[]>) => response.body)
            )
            .subscribe((res: ISession[]) => (this.sessions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.mayorEventsSession.id !== undefined) {
            this.subscribeToSaveResponse(this.mayorEventsSessionService.update(this.mayorEventsSession));
        } else {
            this.subscribeToSaveResponse(this.mayorEventsSessionService.create(this.mayorEventsSession));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMayorEventsSession>>) {
        result.subscribe((res: HttpResponse<IMayorEventsSession>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSessionById(index: number, item: ISession) {
        return item.id;
    }
}
