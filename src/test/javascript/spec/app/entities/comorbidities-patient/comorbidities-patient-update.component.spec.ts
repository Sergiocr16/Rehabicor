/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { ComorbiditiesPatientUpdateComponent } from 'app/entities/comorbidities-patient/comorbidities-patient-update.component';
import { ComorbiditiesPatientService } from 'app/entities/comorbidities-patient/comorbidities-patient.service';
import { ComorbiditiesPatient } from 'app/shared/model/comorbidities-patient.model';

describe('Component Tests', () => {
    describe('ComorbiditiesPatient Management Update Component', () => {
        let comp: ComorbiditiesPatientUpdateComponent;
        let fixture: ComponentFixture<ComorbiditiesPatientUpdateComponent>;
        let service: ComorbiditiesPatientService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [ComorbiditiesPatientUpdateComponent]
            })
                .overrideTemplate(ComorbiditiesPatientUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ComorbiditiesPatientUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComorbiditiesPatientService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ComorbiditiesPatient(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.comorbiditiesPatient = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ComorbiditiesPatient();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.comorbiditiesPatient = entity;
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
