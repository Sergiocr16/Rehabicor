/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { IncomeDiagnosisPatientUpdateComponent } from 'app/entities/income-diagnosis-patient/income-diagnosis-patient-update.component';
import { IncomeDiagnosisPatientService } from 'app/entities/income-diagnosis-patient/income-diagnosis-patient.service';
import { IncomeDiagnosisPatient } from 'app/shared/model/income-diagnosis-patient.model';

describe('Component Tests', () => {
    describe('IncomeDiagnosisPatient Management Update Component', () => {
        let comp: IncomeDiagnosisPatientUpdateComponent;
        let fixture: ComponentFixture<IncomeDiagnosisPatientUpdateComponent>;
        let service: IncomeDiagnosisPatientService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [IncomeDiagnosisPatientUpdateComponent]
            })
                .overrideTemplate(IncomeDiagnosisPatientUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IncomeDiagnosisPatientUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncomeDiagnosisPatientService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new IncomeDiagnosisPatient(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.incomeDiagnosisPatient = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new IncomeDiagnosisPatient();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.incomeDiagnosisPatient = entity;
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
