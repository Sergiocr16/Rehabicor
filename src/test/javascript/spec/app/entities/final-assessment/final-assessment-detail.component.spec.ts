/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { FinalAssessmentDetailComponent } from 'app/entities/final-assessment/final-assessment-detail.component';
import { FinalAssessment } from 'app/shared/model/final-assessment.model';

describe('Component Tests', () => {
    describe('FinalAssessment Management Detail Component', () => {
        let comp: FinalAssessmentDetailComponent;
        let fixture: ComponentFixture<FinalAssessmentDetailComponent>;
        const route = ({ data: of({ finalAssessment: new FinalAssessment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [FinalAssessmentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FinalAssessmentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FinalAssessmentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.finalAssessment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
