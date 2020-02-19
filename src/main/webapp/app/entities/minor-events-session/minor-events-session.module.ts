import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    MinorEventsSessionComponent,
    MinorEventsSessionDetailComponent,
    MinorEventsSessionUpdateComponent,
    MinorEventsSessionDeletePopupComponent,
    MinorEventsSessionDeleteDialogComponent,
    minorEventsSessionRoute,
    minorEventsSessionPopupRoute
} from './';

const ENTITY_STATES = [...minorEventsSessionRoute, ...minorEventsSessionPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MinorEventsSessionComponent,
        MinorEventsSessionDetailComponent,
        MinorEventsSessionUpdateComponent,
        MinorEventsSessionDeleteDialogComponent,
        MinorEventsSessionDeletePopupComponent
    ],
    entryComponents: [
        MinorEventsSessionComponent,
        MinorEventsSessionUpdateComponent,
        MinorEventsSessionDeleteDialogComponent,
        MinorEventsSessionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorMinorEventsSessionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
