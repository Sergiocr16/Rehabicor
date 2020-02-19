import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDepressiveSymptomsSession } from 'app/shared/model/depressive-symptoms-session.model';
import { DepressiveSymptomsSessionService } from './depressive-symptoms-session.service';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session';

@Component({
    selector: 'jhi-depressive-symptoms-session-update',
    templateUrl: './depressive-symptoms-session-update.component.html'
})
export class DepressiveSymptomsSessionUpdateComponent implements OnInit {
    depressiveSymptomsSession: IDepressiveSymptomsSession;
    isSaving: boolean;

    sessions: ISession[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected depressiveSymptomsSessionService: DepressiveSymptomsSessionService,
        protected sessionService: SessionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ depressiveSymptomsSession }) => {
            this.depressiveSymptomsSession = depressiveSymptomsSession;
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
        if (this.depressiveSymptomsSession.id !== undefined) {
            this.subscribeToSaveResponse(this.depressiveSymptomsSessionService.update(this.depressiveSymptomsSession));
        } else {
            this.subscribeToSaveResponse(this.depressiveSymptomsSessionService.create(this.depressiveSymptomsSession));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepressiveSymptomsSession>>) {
        result.subscribe(
            (res: HttpResponse<IDepressiveSymptomsSession>) => this.onSaveSuccess(),
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSessionById(index: number, item: ISession) {
        return item.id;
    }
}
