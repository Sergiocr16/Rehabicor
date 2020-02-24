import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    IncomeDiagnosisComponent,
    IncomeDiagnosisDetailComponent,
    IncomeDiagnosisUpdateComponent,
    IncomeDiagnosisDeletePopupComponent,
    IncomeDiagnosisDeleteDialogComponent,
    incomeDiagnosisRoute,
    incomeDiagnosisPopupRoute
} from './';
import { ReactiveFormsModule } from '@angular/forms';

const ENTITY_STATES = [...incomeDiagnosisRoute, ...incomeDiagnosisPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES), ReactiveFormsModule],
    declarations: [
        IncomeDiagnosisComponent,
        IncomeDiagnosisDetailComponent,
        IncomeDiagnosisUpdateComponent,
        IncomeDiagnosisDeleteDialogComponent,
        IncomeDiagnosisDeletePopupComponent
    ],
    entryComponents: [
        IncomeDiagnosisComponent,
        IncomeDiagnosisUpdateComponent,
        IncomeDiagnosisDeleteDialogComponent,
        IncomeDiagnosisDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorIncomeDiagnosisModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
