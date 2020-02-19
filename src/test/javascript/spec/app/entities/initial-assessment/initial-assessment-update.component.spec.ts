/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { InitialAssessmentUpdateComponent } from 'app/entities/initial-assessment/initial-assessment-update.component';
import { InitialAssessmentService } from 'app/entities/initial-assessment/initial-assessment.service';
import { InitialAssessment } from 'app/shared/model/initial-assessment.model';

describe('Component Tests', () => {
    describe('InitialAssessment Management Update Component', () => {
        let comp: InitialAssessmentUpdateComponent;
        let fixture: ComponentFixture<InitialAssessmentUpdateComponent>;
        let service: InitialAssessmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [InitialAssessmentUpdateComponent]
            })
                .overrideTemplate(InitialAssessmentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InitialAssessmentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InitialAssessmentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new InitialAssessment(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.initialAssessment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new InitialAssessment();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.initialAssessment = entity;
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
