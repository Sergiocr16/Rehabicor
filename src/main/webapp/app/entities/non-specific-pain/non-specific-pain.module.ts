import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    NonSpecificPainComponent,
    NonSpecificPainDetailComponent,
    NonSpecificPainUpdateComponent,
    NonSpecificPainDeletePopupComponent,
    NonSpecificPainDeleteDialogComponent,
    nonSpecificPainRoute,
    nonSpecificPainPopupRoute
} from './';

const ENTITY_STATES = [...nonSpecificPainRoute, ...nonSpecificPainPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NonSpecificPainComponent,
        NonSpecificPainDetailComponent,
        NonSpecificPainUpdateComponent,
        NonSpecificPainDeleteDialogComponent,
        NonSpecificPainDeletePopupComponent
    ],
    entryComponents: [
        NonSpecificPainComponent,
        NonSpecificPainUpdateComponent,
        NonSpecificPainDeleteDialogComponent,
        NonSpecificPainDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorNonSpecificPainModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
