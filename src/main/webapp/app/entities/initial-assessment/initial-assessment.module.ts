import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    InitialAssessmentComponent,
    InitialAssessmentDetailComponent,
    InitialAssessmentUpdateComponent,
    InitialAssessmentDeletePopupComponent,
    InitialAssessmentDeleteDialogComponent,
    initialAssessmentRoute,
    initialAssessmentPopupRoute
} from './';

const ENTITY_STATES = [...initialAssessmentRoute, ...initialAssessmentPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InitialAssessmentComponent,
        InitialAssessmentDetailComponent,
        InitialAssessmentUpdateComponent,
        InitialAssessmentDeleteDialogComponent,
        InitialAssessmentDeletePopupComponent
    ],
    entryComponents: [
        InitialAssessmentComponent,
        InitialAssessmentUpdateComponent,
        InitialAssessmentDeleteDialogComponent,
        InitialAssessmentDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorInitialAssessmentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
