/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { InitialAssessmentDetailComponent } from 'app/entities/initial-assessment/initial-assessment-detail.component';
import { InitialAssessment } from 'app/shared/model/initial-assessment.model';

describe('Component Tests', () => {
    describe('InitialAssessment Management Detail Component', () => {
        let comp: InitialAssessmentDetailComponent;
        let fixture: ComponentFixture<InitialAssessmentDetailComponent>;
        const route = ({ data: of({ initialAssessment: new InitialAssessment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [InitialAssessmentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InitialAssessmentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InitialAssessmentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.initialAssessment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
