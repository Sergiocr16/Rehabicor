import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMinorEvent } from 'app/shared/model/minor-event.model';

@Component({
    selector: 'jhi-minor-event-detail',
    templateUrl: './minor-event-detail.component.html'
})
export class MinorEventDetailComponent implements OnInit {
    minorEvent: IMinorEvent;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ minorEvent }) => {
            this.minorEvent = minorEvent;
        });
    }

    previousState() {
        window.history.back();
    }
}
