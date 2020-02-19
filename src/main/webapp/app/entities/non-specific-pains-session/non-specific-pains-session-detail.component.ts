import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INonSpecificPainsSession } from 'app/shared/model/non-specific-pains-session.model';

@Component({
    selector: 'jhi-non-specific-pains-session-detail',
    templateUrl: './non-specific-pains-session-detail.component.html'
})
export class NonSpecificPainsSessionDetailComponent implements OnInit {
    nonSpecificPainsSession: INonSpecificPainsSession;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ nonSpecificPainsSession }) => {
            this.nonSpecificPainsSession = nonSpecificPainsSession;
        });
    }

    previousState() {
        window.history.back();
    }
}
