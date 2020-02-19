import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComorbiditiesPatient } from 'app/shared/model/comorbidities-patient.model';

@Component({
    selector: 'jhi-comorbidities-patient-detail',
    templateUrl: './comorbidities-patient-detail.component.html'
})
export class ComorbiditiesPatientDetailComponent implements OnInit {
    comorbiditiesPatient: IComorbiditiesPatient;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ comorbiditiesPatient }) => {
            this.comorbiditiesPatient = comorbiditiesPatient;
        });
    }

    previousState() {
        window.history.back();
    }
}
