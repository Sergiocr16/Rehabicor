import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    ComorbiditiesPatientComponent,
    ComorbiditiesPatientDetailComponent,
    ComorbiditiesPatientUpdateComponent,
    ComorbiditiesPatientDeletePopupComponent,
    ComorbiditiesPatientDeleteDialogComponent,
    comorbiditiesPatientRoute,
    comorbiditiesPatientPopupRoute
} from './';

const ENTITY_STATES = [...comorbiditiesPatientRoute, ...comorbiditiesPatientPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ComorbiditiesPatientComponent,
        ComorbiditiesPatientDetailComponent,
        ComorbiditiesPatientUpdateComponent,
        ComorbiditiesPatientDeleteDialogComponent,
        ComorbiditiesPatientDeletePopupComponent
    ],
    entryComponents: [
        ComorbiditiesPatientComponent,
        ComorbiditiesPatientUpdateComponent,
        ComorbiditiesPatientDeleteDialogComponent,
        ComorbiditiesPatientDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorComorbiditiesPatientModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
