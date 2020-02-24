import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISession } from 'app/shared/model/session.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IPatient, Patient } from 'app/shared/model/patient.model';
import { NonSpecificPainsSessionService } from 'app/entities/non-specific-pains-session/non-specific-pains-session.service';
import { DepressiveSymptomsSessionService } from 'app/entities/depressive-symptoms-session/depressive-symptoms-session.service';
import { MayorEventsSessionService } from 'app/entities/mayor-events-session/mayor-events-session.service';
import { MinorEventsSessionService } from 'app/entities/minor-events-session/minor-events-session.service';
import { INonSpecificPainsSession } from 'app/shared/model/non-specific-pains-session.model';
import { IMayorEventsSession } from 'app/shared/model/mayor-events-session.model';
import { IMinorEventsSession } from 'app/shared/model/minor-events-session.model';
import { IDepressiveSymptomsSession } from 'app/shared/model/depressive-symptoms-session.model';

@Component({
    selector: 'jhi-session-detail',
    templateUrl: './session-detail.component.html'
})
export class SessionDetailComponent implements OnInit {
    session: ISession;
    patient: IPatient;
    minorEventSessions = [];
    mayorEventSessions = [];
    nonSpecificPainSessions = [];
    depressiveSymptomsSessions = [];

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected patientService: PatientService,
        protected nonSpecificPainsSessionService: NonSpecificPainsSessionService,
        protected depressiveSymptomsSessionService: DepressiveSymptomsSessionService,
        protected mayorEventsSessionService: MayorEventsSessionService,
        protected minorEventsSessionService: MinorEventsSessionService
    ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ session }) => {
            this.session = session;
            this.patientService
                .find(session.patientId)
                .pipe(
                    filter((response: HttpResponse<Patient>) => response.ok),
                    map((patient: HttpResponse<Patient>) => patient.body)
                )
                .subscribe(patient => {
                    this.patient = patient;
                });
            this.loadNonSpecificPainSessions(this.session.id);
            this.loadMayorEventsSession(this.session.id);
            this.loadMinorEventsSessions(this.session.id);
            this.loadDepressiveSymptomsSessions(this.session.id);
        });
    }
    loadNonSpecificPainSessions(sessionId) {
        this.nonSpecificPainsSessionService
            .queryBySession({ sessionId })
            .pipe(
                filter((mayBeOk: HttpResponse<INonSpecificPainsSession[]>) => mayBeOk.ok),
                map((response: HttpResponse<INonSpecificPainsSession[]>) => response.body)
            )
            .subscribe(
                (res: INonSpecificPainsSession[]) => (this.nonSpecificPainSessions = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    loadMayorEventsSession(sessionId) {
        this.mayorEventsSessionService
            .queryBySession({ sessionId })
            .pipe(
                filter((mayBeOk: HttpResponse<IMayorEventsSession[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMayorEventsSession[]>) => response.body)
            )
            .subscribe(
                (res: IMayorEventsSession[]) => (this.mayorEventSessions = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    loadMinorEventsSessions(sessionId) {
        this.minorEventsSessionService
            .queryBySession({ sessionId })
            .pipe(
                filter((mayBeOk: HttpResponse<IMinorEventsSession[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMinorEventsSession[]>) => response.body)
            )
            .subscribe(
                (res: IMinorEventsSession[]) => (this.minorEventSessions = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    loadDepressiveSymptomsSessions(sessionId) {
        this.depressiveSymptomsSessionService
            .queryBySession({ sessionId })
            .pipe(
                filter((mayBeOk: HttpResponse<IDepressiveSymptomsSession[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepressiveSymptomsSession[]>) => response.body)
            )
            .subscribe(
                (res: IDepressiveSymptomsSession[]) => (this.depressiveSymptomsSessions = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    previousState() {
        window.history.back();
    }
    protected onError(errorMessage: string) {}
}
