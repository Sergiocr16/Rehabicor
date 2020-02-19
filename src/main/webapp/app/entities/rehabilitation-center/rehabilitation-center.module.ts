import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    RehabilitationCenterComponent,
    RehabilitationCenterDetailComponent,
    RehabilitationCenterUpdateComponent,
    RehabilitationCenterDeletePopupComponent,
    RehabilitationCenterDeleteDialogComponent,
    rehabilitationCenterRoute,
    rehabilitationCenterPopupRoute
} from './';

const ENTITY_STATES = [...rehabilitationCenterRoute, ...rehabilitationCenterPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RehabilitationCenterComponent,
        RehabilitationCenterDetailComponent,
        RehabilitationCenterUpdateComponent,
        RehabilitationCenterDeleteDialogComponent,
        RehabilitationCenterDeletePopupComponent
    ],
    entryComponents: [
        RehabilitationCenterComponent,
        RehabilitationCenterUpdateComponent,
        RehabilitationCenterDeleteDialogComponent,
        RehabilitationCenterDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorRehabilitationCenterModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
