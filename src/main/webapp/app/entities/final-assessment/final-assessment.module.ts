import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    FinalAssessmentComponent,
    FinalAssessmentDetailComponent,
    FinalAssessmentUpdateComponent,
    FinalAssessmentDeletePopupComponent,
    FinalAssessmentDeleteDialogComponent,
    finalAssessmentRoute,
    finalAssessmentPopupRoute
} from './';
import { ReactiveFormsModule } from '@angular/forms';

const ENTITY_STATES = [...finalAssessmentRoute, ...finalAssessmentPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES), ReactiveFormsModule],
    declarations: [
        FinalAssessmentComponent,
        FinalAssessmentDetailComponent,
        FinalAssessmentUpdateComponent,
        FinalAssessmentDeleteDialogComponent,
        FinalAssessmentDeletePopupComponent
    ],
    entryComponents: [
        FinalAssessmentComponent,
        FinalAssessmentUpdateComponent,
        FinalAssessmentDeleteDialogComponent,
        FinalAssessmentDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorFinalAssessmentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
