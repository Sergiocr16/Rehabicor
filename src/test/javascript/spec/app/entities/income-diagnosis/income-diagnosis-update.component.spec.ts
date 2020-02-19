/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { IncomeDiagnosisUpdateComponent } from 'app/entities/income-diagnosis/income-diagnosis-update.component';
import { IncomeDiagnosisService } from 'app/entities/income-diagnosis/income-diagnosis.service';
import { IncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';

describe('Component Tests', () => {
    describe('IncomeDiagnosis Management Update Component', () => {
        let comp: IncomeDiagnosisUpdateComponent;
        let fixture: ComponentFixture<IncomeDiagnosisUpdateComponent>;
        let service: IncomeDiagnosisService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [IncomeDiagnosisUpdateComponent]
            })
                .overrideTemplate(IncomeDiagnosisUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IncomeDiagnosisUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncomeDiagnosisService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new IncomeDiagnosis(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.incomeDiagnosis = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new IncomeDiagnosis();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.incomeDiagnosis = entity;
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
