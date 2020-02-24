import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    RehabilitationGroupComponent,
    RehabilitationGroupDetailComponent,
    RehabilitationGroupUpdateComponent,
    RehabilitationGroupDeletePopupComponent,
    RehabilitationGroupDeleteDialogComponent,
    rehabilitationGroupRoute,
    rehabilitationGroupPopupRoute
} from './';
import { ReactiveFormsModule } from '@angular/forms';

const ENTITY_STATES = [...rehabilitationGroupRoute, ...rehabilitationGroupPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES), ReactiveFormsModule],
    declarations: [
        RehabilitationGroupComponent,
        RehabilitationGroupDetailComponent,
        RehabilitationGroupUpdateComponent,
        RehabilitationGroupDeleteDialogComponent,
        RehabilitationGroupDeletePopupComponent
    ],
    entryComponents: [
        RehabilitationGroupComponent,
        RehabilitationGroupUpdateComponent,
        RehabilitationGroupDeleteDialogComponent,
        RehabilitationGroupDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorRehabilitationGroupModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
