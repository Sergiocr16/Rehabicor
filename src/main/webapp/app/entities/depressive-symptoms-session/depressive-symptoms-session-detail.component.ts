import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepressiveSymptomsSession } from 'app/shared/model/depressive-symptoms-session.model';

@Component({
    selector: 'jhi-depressive-symptoms-session-detail',
    templateUrl: './depressive-symptoms-session-detail.component.html'
})
export class DepressiveSymptomsSessionDetailComponent implements OnInit {
    depressiveSymptomsSession: IDepressiveSymptomsSession;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depressiveSymptomsSession }) => {
            this.depressiveSymptomsSession = depressiveSymptomsSession;
        });
    }

    previousState() {
        window.history.back();
    }
}
