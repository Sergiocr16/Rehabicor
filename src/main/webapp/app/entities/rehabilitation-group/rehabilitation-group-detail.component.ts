import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';

@Component({
    selector: 'jhi-rehabilitation-group-detail',
    templateUrl: './rehabilitation-group-detail.component.html'
})
export class RehabilitationGroupDetailComponent implements OnInit {
    rehabilitationGroup: IRehabilitationGroup;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rehabilitationGroup }) => {
            this.rehabilitationGroup = rehabilitationGroup;
        });
    }

    previousState() {
        window.history.back();
    }
}
