import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INonSpecificPainsSession } from 'app/shared/model/non-specific-pains-session.model';
import { NonSpecificPainsSessionService } from './non-specific-pains-session.service';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session';

@Component({
    selector: 'jhi-non-specific-pains-session-update',
    templateUrl: './non-specific-pains-session-update.component.html'
})
export class NonSpecificPainsSessionUpdateComponent implements OnInit {
    nonSpecificPainsSession: INonSpecificPainsSession;
    isSaving: boolean;

    sessions: ISession[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected nonSpecificPainsSessionService: NonSpecificPainsSessionService,
        protected sessionService: SessionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ nonSpecificPainsSession }) => {
            this.nonSpecificPainsSession = nonSpecificPainsSession;
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
        if (this.nonSpecificPainsSession.id !== undefined) {
            this.subscribeToSaveResponse(this.nonSpecificPainsSessionService.update(this.nonSpecificPainsSession));
        } else {
            this.subscribeToSaveResponse(this.nonSpecificPainsSessionService.create(this.nonSpecificPainsSession));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<INonSpecificPainsSession>>) {
        result.subscribe(
            (res: HttpResponse<INonSpecificPainsSession>) => this.onSaveSuccess(),
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
