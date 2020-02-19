import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInitialAssessment } from 'app/shared/model/initial-assessment.model';

@Component({
    selector: 'jhi-initial-assessment-detail',
    templateUrl: './initial-assessment-detail.component.html'
})
export class InitialAssessmentDetailComponent implements OnInit {
    initialAssessment: IInitialAssessment;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ initialAssessment }) => {
            this.initialAssessment = initialAssessment;
        });
    }

    previousState() {
        window.history.back();
    }
}
