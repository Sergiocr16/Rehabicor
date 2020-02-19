/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { FinalAssessmentUpdateComponent } from 'app/entities/final-assessment/final-assessment-update.component';
import { FinalAssessmentService } from 'app/entities/final-assessment/final-assessment.service';
import { FinalAssessment } from 'app/shared/model/final-assessment.model';

describe('Component Tests', () => {
    describe('FinalAssessment Management Update Component', () => {
        let comp: FinalAssessmentUpdateComponent;
        let fixture: ComponentFixture<FinalAssessmentUpdateComponent>;
        let service: FinalAssessmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [FinalAssessmentUpdateComponent]
            })
                .overrideTemplate(FinalAssessmentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FinalAssessmentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinalAssessmentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new FinalAssessment(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.finalAssessment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new FinalAssessment();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.finalAssessment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
