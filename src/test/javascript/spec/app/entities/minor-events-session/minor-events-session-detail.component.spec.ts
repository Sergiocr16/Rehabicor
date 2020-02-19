/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { MinorEventsSessionDetailComponent } from 'app/entities/minor-events-session/minor-events-session-detail.component';
import { MinorEventsSession } from 'app/shared/model/minor-events-session.model';

describe('Component Tests', () => {
    describe('MinorEventsSession Management Detail Component', () => {
        let comp: MinorEventsSessionDetailComponent;
        let fixture: ComponentFixture<MinorEventsSessionDetailComponent>;
        const route = ({ data: of({ minorEventsSession: new MinorEventsSession(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [MinorEventsSessionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MinorEventsSessionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MinorEventsSessionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.minorEventsSession).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
