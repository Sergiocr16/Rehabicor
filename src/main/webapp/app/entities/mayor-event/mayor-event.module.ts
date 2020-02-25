import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    MayorEventComponent,
    MayorEventDetailComponent,
    MayorEventUpdateComponent,
    MayorEventDeletePopupComponent,
    MayorEventDeleteDialogComponent,
    mayorEventRoute,
    mayorEventPopupRoute
} from './';
import { ReactiveFormsModule } from '@angular/forms';

const ENTITY_STATES = [...mayorEventRoute, ...mayorEventPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES), ReactiveFormsModule],
    declarations: [
        MayorEventComponent,
        MayorEventDetailComponent,
        MayorEventUpdateComponent,
        MayorEventDeleteDialogComponent,
        MayorEventDeletePopupComponent
    ],
    entryComponents: [MayorEventComponent, MayorEventUpdateComponent, MayorEventDeleteDialogComponent, MayorEventDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorMayorEventModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
