import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RehabicorSharedModule } from 'app/shared';
import {
    DepressiveSymptomsSessionComponent,
    DepressiveSymptomsSessionDetailComponent,
    DepressiveSymptomsSessionUpdateComponent,
    DepressiveSymptomsSessionDeletePopupComponent,
    DepressiveSymptomsSessionDeleteDialogComponent,
    depressiveSymptomsSessionRoute,
    depressiveSymptomsSessionPopupRoute
} from './';

const ENTITY_STATES = [...depressiveSymptomsSessionRoute, ...depressiveSymptomsSessionPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DepressiveSymptomsSessionComponent,
        DepressiveSymptomsSessionDetailComponent,
        DepressiveSymptomsSessionUpdateComponent,
        DepressiveSymptomsSessionDeleteDialogComponent,
        DepressiveSymptomsSessionDeletePopupComponent
    ],
    entryComponents: [
        DepressiveSymptomsSessionComponent,
        DepressiveSymptomsSessionUpdateComponent,
        DepressiveSymptomsSessionDeleteDialogComponent,
        DepressiveSymptomsSessionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorDepressiveSymptomsSessionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
