import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RehabicorSharedModule } from 'app/shared/shared.module';
import { RehabilitationGroupPanelDataComponent } from './rehabilitation-group-panel-data.component';
import { ChartsModule } from 'ng2-charts';

import { rehabilitationGroupPanelDataRoute } from './rehabilitation-group-panel-data.route';
import { RehabilitationGroupDetailPanelDataComponent } from 'app/entities/panel-data/rehabilitation-group-detail.component';

const ENTITY_STATES = [...rehabilitationGroupPanelDataRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES), ChartsModule],
    declarations: [
        RehabilitationGroupPanelDataComponent,
        RehabilitationGroupDetailPanelDataComponent,
        RehabilitationGroupPanelDataComponent
    ],
    entryComponents: []
})
export class RehabicorRehabilitationGroupModule {}
