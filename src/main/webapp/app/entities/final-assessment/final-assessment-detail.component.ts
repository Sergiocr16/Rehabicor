import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFinalAssessment } from 'app/shared/model/final-assessment.model';

@Component({
    selector: 'jhi-final-assessment-detail',
    templateUrl: './final-assessment-detail.component.html'
})
export class FinalAssessmentDetailComponent implements OnInit {
    finalAssessment: IFinalAssessment;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ finalAssessment }) => {
            this.finalAssessment = finalAssessment;
        });
    }

    previousState() {
        window.history.back();
    }
}
