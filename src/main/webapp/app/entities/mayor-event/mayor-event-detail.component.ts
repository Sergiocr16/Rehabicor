import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMayorEvent } from 'app/shared/model/mayor-event.model';

@Component({
    selector: 'jhi-mayor-event-detail',
    templateUrl: './mayor-event-detail.component.html'
})
export class MayorEventDetailComponent implements OnInit {
    mayorEvent: IMayorEvent;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mayorEvent }) => {
            this.mayorEvent = mayorEvent;
        });
    }

    previousState() {
        window.history.back();
    }
}
