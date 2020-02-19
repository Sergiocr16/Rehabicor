/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { NonSpecificPainsSessionDetailComponent } from 'app/entities/non-specific-pains-session/non-specific-pains-session-detail.component';
import { NonSpecificPainsSession } from 'app/shared/model/non-specific-pains-session.model';

describe('Component Tests', () => {
    describe('NonSpecificPainsSession Management Detail Component', () => {
        let comp: NonSpecificPainsSessionDetailComponent;
        let fixture: ComponentFixture<NonSpecificPainsSessionDetailComponent>;
        const route = ({ data: of({ nonSpecificPainsSession: new NonSpecificPainsSession(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [NonSpecificPainsSessionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NonSpecificPainsSessionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NonSpecificPainsSessionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.nonSpecificPainsSession).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
