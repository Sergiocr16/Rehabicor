import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncomeDiagnosisPatient } from 'app/shared/model/income-diagnosis-patient.model';

@Component({
    selector: 'jhi-income-diagnosis-patient-detail',
    templateUrl: './income-diagnosis-patient-detail.component.html'
})
export class IncomeDiagnosisPatientDetailComponent implements OnInit {
    incomeDiagnosisPatient: IIncomeDiagnosisPatient;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incomeDiagnosisPatient }) => {
            this.incomeDiagnosisPatient = incomeDiagnosisPatient;
        });
    }

    previousState() {
        window.history.back();
    }
}
