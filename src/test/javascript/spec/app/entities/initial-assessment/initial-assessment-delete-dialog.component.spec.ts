/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RehabicorTestModule } from '../../../test.module';
import { InitialAssessmentDeleteDialogComponent } from 'app/entities/initial-assessment/initial-assessment-delete-dialog.component';
import { InitialAssessmentService } from 'app/entities/initial-assessment/initial-assessment.service';

describe('Component Tests', () => {
    describe('InitialAssessment Management Delete Component', () => {
        let comp: InitialAssessmentDeleteDialogComponent;
        let fixture: ComponentFixture<InitialAssessmentDeleteDialogComponent>;
        let service: InitialAssessmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [InitialAssessmentDeleteDialogComponent]
            })
                .overrideTemplate(InitialAssessmentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InitialAssessmentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InitialAssessmentService);
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
