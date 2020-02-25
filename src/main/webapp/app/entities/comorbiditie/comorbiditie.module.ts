import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    ComorbiditieComponent,
    ComorbiditieDetailComponent,
    ComorbiditieUpdateComponent,
    ComorbiditieDeletePopupComponent,
    ComorbiditieDeleteDialogComponent,
    comorbiditieRoute,
    comorbiditiePopupRoute
} from './';
import { ReactiveFormsModule } from '@angular/forms';

const ENTITY_STATES = [...comorbiditieRoute, ...comorbiditiePopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES), ReactiveFormsModule],
    declarations: [
        ComorbiditieComponent,
        ComorbiditieDetailComponent,
        ComorbiditieUpdateComponent,
        ComorbiditieDeleteDialogComponent,
        ComorbiditieDeletePopupComponent
    ],
    entryComponents: [
        ComorbiditieComponent,
        ComorbiditieUpdateComponent,
        ComorbiditieDeleteDialogComponent,
        ComorbiditieDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorComorbiditieModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
