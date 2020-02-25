import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RehabicorSharedModule } from 'app/shared/shared.module';
import { RehabilitationGroupEvaluationComponent } from './rehabilitation-group-evaluation.component';
import { ChartsModule } from 'ng2-charts';

import { rehabilitationGroupEvaluationRoute } from './rehabilitation-group-evaluation.route';
import { RehabilitationGroupDetailEvaluationComponent } from 'app/entities/evaluation/rehabilitation-group-evaluation-detail.component';

const ENTITY_STATES = [...rehabilitationGroupEvaluationRoute];

@NgModule({
    imports: [RehabicorSharedModule, RouterModule.forChild(ENTITY_STATES), ChartsModule],
    declarations: [
        RehabilitationGroupEvaluationComponent,
        RehabilitationGroupDetailEvaluationComponent,
        RehabilitationGroupEvaluationComponent
    ],
    entryComponents: []
})
export class RehabicorRehabilitationGroupModule {}
