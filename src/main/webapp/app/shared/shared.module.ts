import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { RehabicorSharedLibsModule, RehabicorSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { MaterialModule } from 'app/shared/util/material.module';
import { ChartsModule } from 'ng2-charts';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NoContentComponent } from 'app/layouts/no-content/no-content.component';

@NgModule({
    imports: [RehabicorSharedLibsModule, RehabicorSharedCommonModule, MaterialModule, FlexLayoutModule, ChartsModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, NoContentComponent],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        RehabicorSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        MaterialModule,
        FlexLayoutModule,
        NoContentComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RehabicorSharedModule {
    static forRoot() {
        return {
            ngModule: RehabicorSharedModule
        };
    }
}
