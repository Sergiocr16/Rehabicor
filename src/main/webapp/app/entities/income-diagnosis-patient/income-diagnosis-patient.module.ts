import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    IncomeDiagnosisPatientComponent,
    IncomeDiagnosisPatientDetailComponent,
    IncomeDiagnosisPatientUpdateComponent,
    IncomeDiagnosisPatientDeletePopupComponent,
    IncomeDiagnosisPatientDeleteDialogComponent,
    incomeDiagnosisPatientRoute,
    incomeDiagnosisPatientPopupRoute
} from './';

const ENTITY_STATES = [...incomeDiagnosisPatientRoute, ...incomeDiagnosisPatientPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IncomeDiagnosisPatientComponent,
        IncomeDiagnosisPatientDetailComponent,
        IncomeDiagnosisPatientUpdateComponent,
        IncomeDiagnosisPatientDeleteDialogComponent,
        IncomeDiagnosisPatientDeletePopupComponent
    ],
    entryComponents: [
        IncomeDiagnosisPatientComponent,
        IncomeDiagnosisPatientUpdateComponent,
        IncomeDiagnosisPatientDeleteDialogComponent,
        IncomeDiagnosisPatientDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorIncomeDiagnosisPatientModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
