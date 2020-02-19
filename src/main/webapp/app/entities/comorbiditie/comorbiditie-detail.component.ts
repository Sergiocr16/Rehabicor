import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComorbiditie } from 'app/shared/model/comorbiditie.model';

@Component({
    selector: 'jhi-comorbiditie-detail',
    templateUrl: './comorbiditie-detail.component.html'
})
export class ComorbiditieDetailComponent implements OnInit {
    comorbiditie: IComorbiditie;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ comorbiditie }) => {
            this.comorbiditie = comorbiditie;
        });
    }

    previousState() {
        window.history.back();
    }
}
