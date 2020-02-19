import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMinorEventsSession } from 'app/shared/model/minor-events-session.model';

@Component({
    selector: 'jhi-minor-events-session-detail',
    templateUrl: './minor-events-session-detail.component.html'
})
export class MinorEventsSessionDetailComponent implements OnInit {
    minorEventsSession: IMinorEventsSession;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ minorEventsSession }) => {
            this.minorEventsSession = minorEventsSession;
        });
    }

    previousState() {
        window.history.back();
    }
}
