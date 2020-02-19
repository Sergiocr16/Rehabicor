/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { DepressiveSymptomUpdateComponent } from 'app/entities/depressive-symptom/depressive-symptom-update.component';
import { DepressiveSymptomService } from 'app/entities/depressive-symptom/depressive-symptom.service';
import { DepressiveSymptom } from 'app/shared/model/depressive-symptom.model';

describe('Component Tests', () => {
    describe('DepressiveSymptom Management Update Component', () => {
        let comp: DepressiveSymptomUpdateComponent;
        let fixture: ComponentFixture<DepressiveSymptomUpdateComponent>;
        let service: DepressiveSymptomService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [DepressiveSymptomUpdateComponent]
            })
                .overrideTemplate(DepressiveSymptomUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepressiveSymptomUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepressiveSymptomService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DepressiveSymptom(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.depressiveSymptom = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DepressiveSymptom();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.depressiveSymptom = entity;
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
