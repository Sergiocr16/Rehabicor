import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';

@Component({
    selector: 'jhi-income-diagnosis-detail',
    templateUrl: './income-diagnosis-detail.component.html'
})
export class IncomeDiagnosisDetailComponent implements OnInit {
    incomeDiagnosis: IIncomeDiagnosis;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incomeDiagnosis }) => {
            this.incomeDiagnosis = incomeDiagnosis;
        });
    }

    previousState() {
        window.history.back();
    }
}
