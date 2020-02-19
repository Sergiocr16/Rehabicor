import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMinorEventsSession } from 'app/shared/model/minor-events-session.model';
import { MinorEventsSessionService } from './minor-events-session.service';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session';

@Component({
    selector: 'jhi-minor-events-session-update',
    templateUrl: './minor-events-session-update.component.html'
})
export class MinorEventsSessionUpdateComponent implements OnInit {
    minorEventsSession: IMinorEventsSession;
    isSaving: boolean;

    sessions: ISession[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected minorEventsSessionService: MinorEventsSessionService,
        protected sessionService: SessionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ minorEventsSession }) => {
            this.minorEventsSession = minorEventsSession;
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
        if (this.minorEventsSession.id !== undefined) {
            this.subscribeToSaveResponse(this.minorEventsSessionService.update(this.minorEventsSession));
        } else {
            this.subscribeToSaveResponse(this.minorEventsSessionService.create(this.minorEventsSession));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMinorEventsSession>>) {
        result.subscribe((res: HttpResponse<IMinorEventsSession>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
