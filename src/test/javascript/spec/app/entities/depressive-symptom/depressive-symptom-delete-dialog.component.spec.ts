/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RehabicorTestModule } from '../../../test.module';
import { DepressiveSymptomDeleteDialogComponent } from 'app/entities/depressive-symptom/depressive-symptom-delete-dialog.component';
import { DepressiveSymptomService } from 'app/entities/depressive-symptom/depressive-symptom.service';

describe('Component Tests', () => {
    describe('DepressiveSymptom Management Delete Component', () => {
        let comp: DepressiveSymptomDeleteDialogComponent;
        let fixture: ComponentFixture<DepressiveSymptomDeleteDialogComponent>;
        let service: DepressiveSymptomService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [DepressiveSymptomDeleteDialogComponent]
            })
                .overrideTemplate(DepressiveSymptomDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepressiveSymptomDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepressiveSymptomService);
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
