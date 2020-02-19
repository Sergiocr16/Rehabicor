import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    MinorEventComponent,
    MinorEventDetailComponent,
    MinorEventUpdateComponent,
    MinorEventDeletePopupComponent,
    MinorEventDeleteDialogComponent,
    minorEventRoute,
    minorEventPopupRoute
} from './';

const ENTITY_STATES = [...minorEventRoute, ...minorEventPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MinorEventComponent,
        MinorEventDetailComponent,
        MinorEventUpdateComponent,
        MinorEventDeleteDialogComponent,
        MinorEventDeletePopupComponent
    ],
    entryComponents: [MinorEventComponent, MinorEventUpdateComponent, MinorEventDeleteDialogComponent, MinorEventDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorMinorEventModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
