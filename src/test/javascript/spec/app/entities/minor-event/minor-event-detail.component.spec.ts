/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { MinorEventDetailComponent } from 'app/entities/minor-event/minor-event-detail.component';
import { MinorEvent } from 'app/shared/model/minor-event.model';

describe('Component Tests', () => {
    describe('MinorEvent Management Detail Component', () => {
        let comp: MinorEventDetailComponent;
        let fixture: ComponentFixture<MinorEventDetailComponent>;
        const route = ({ data: of({ minorEvent: new MinorEvent(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [MinorEventDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MinorEventDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MinorEventDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.minorEvent).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
