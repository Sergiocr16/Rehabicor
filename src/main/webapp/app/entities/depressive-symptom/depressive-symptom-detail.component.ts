import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepressiveSymptom } from 'app/shared/model/depressive-symptom.model';

@Component({
    selector: 'jhi-depressive-symptom-detail',
    templateUrl: './depressive-symptom-detail.component.html'
})
export class DepressiveSymptomDetailComponent implements OnInit {
    depressiveSymptom: IDepressiveSymptom;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depressiveSymptom }) => {
            this.depressiveSymptom = depressiveSymptom;
        });
    }

    previousState() {
        window.history.back();
    }
}
