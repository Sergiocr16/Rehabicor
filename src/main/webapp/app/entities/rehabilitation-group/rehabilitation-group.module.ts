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
import { RehabilitationGroupPanelComponent } from 'app/entities/rehabilitation-group/rehabilitation-group-panel.component';
import { RehabilitationGroupClinicalCharacteristicsComponent } from 'app/entities/rehabilitation-group/rehabilitation-group-clinical-characteristics.component';
import { ChartsModule } from 'ng2-charts';

const ENTITY_STATES = [...rehabilitationGroupRoute, ...rehabilitationGroupPopupRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES), ReactiveFormsModule, ChartsModule],
    declarations: [
        RehabilitationGroupComponent,
        RehabilitationGroupDetailComponent,
        RehabilitationGroupUpdateComponent,
        RehabilitationGroupDeleteDialogComponent,
        RehabilitationGroupDeletePopupComponent,
        RehabilitationGroupPanelComponent,
        RehabilitationGroupClinicalCharacteristicsComponent
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
