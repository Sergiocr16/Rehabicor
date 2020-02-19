/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { MayorEventsSessionDetailComponent } from 'app/entities/mayor-events-session/mayor-events-session-detail.component';
import { MayorEventsSession } from 'app/shared/model/mayor-events-session.model';

describe('Component Tests', () => {
    describe('MayorEventsSession Management Detail Component', () => {
        let comp: MayorEventsSessionDetailComponent;
        let fixture: ComponentFixture<MayorEventsSessionDetailComponent>;
        const route = ({ data: of({ mayorEventsSession: new MayorEventsSession(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [MayorEventsSessionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MayorEventsSessionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MayorEventsSessionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mayorEventsSession).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
