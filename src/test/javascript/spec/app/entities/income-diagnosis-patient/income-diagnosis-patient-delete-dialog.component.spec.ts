/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RehabicorTestModule } from '../../../test.module';
import { IncomeDiagnosisPatientDeleteDialogComponent } from 'app/entities/income-diagnosis-patient/income-diagnosis-patient-delete-dialog.component';
import { IncomeDiagnosisPatientService } from 'app/entities/income-diagnosis-patient/income-diagnosis-patient.service';

describe('Component Tests', () => {
    describe('IncomeDiagnosisPatient Management Delete Component', () => {
        let comp: IncomeDiagnosisPatientDeleteDialogComponent;
        let fixture: ComponentFixture<IncomeDiagnosisPatientDeleteDialogComponent>;
        let service: IncomeDiagnosisPatientService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [IncomeDiagnosisPatientDeleteDialogComponent]
            })
                .overrideTemplate(IncomeDiagnosisPatientDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IncomeDiagnosisPatientDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncomeDiagnosisPatientService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
