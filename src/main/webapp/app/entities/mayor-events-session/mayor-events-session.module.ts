import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    MayorEventsSessionComponent,
    MayorEventsSessionDetailComponent,
    MayorEventsSessionUpdateComponent,
    MayorEventsSessionDeletePopupComponent,
    MayorEventsSessionDeleteDialogComponent,
    mayorEventsSessionRoute,
    mayorEventsSessionPopupRoute
} from './';

const ENTITY_STATES = [...mayorEventsSessionRoute, ...mayorEventsSessionPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MayorEventsSessionComponent,
        MayorEventsSessionDetailComponent,
        MayorEventsSessionUpdateComponent,
        MayorEventsSessionDeleteDialogComponent,
        MayorEventsSessionDeletePopupComponent
    ],
    entryComponents: [
        MayorEventsSessionComponent,
        MayorEventsSessionUpdateComponent,
        MayorEventsSessionDeleteDialogComponent,
        MayorEventsSessionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorMayorEventsSessionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
