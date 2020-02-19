import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    NonSpecificPainsSessionComponent,
    NonSpecificPainsSessionDetailComponent,
    NonSpecificPainsSessionUpdateComponent,
    NonSpecificPainsSessionDeletePopupComponent,
    NonSpecificPainsSessionDeleteDialogComponent,
    nonSpecificPainsSessionRoute,
    nonSpecificPainsSessionPopupRoute
} from './';

const ENTITY_STATES = [...nonSpecificPainsSessionRoute, ...nonSpecificPainsSessionPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NonSpecificPainsSessionComponent,
        NonSpecificPainsSessionDetailComponent,
        NonSpecificPainsSessionUpdateComponent,
        NonSpecificPainsSessionDeleteDialogComponent,
        NonSpecificPainsSessionDeletePopupComponent
    ],
    entryComponents: [
        NonSpecificPainsSessionComponent,
        NonSpecificPainsSessionUpdateComponent,
        NonSpecificPainsSessionDeleteDialogComponent,
        NonSpecificPainsSessionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorNonSpecificPainsSessionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
