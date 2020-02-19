/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RehabicorTestModule } from '../../../test.module';
import { NonSpecificPainDeleteDialogComponent } from 'app/entities/non-specific-pain/non-specific-pain-delete-dialog.component';
import { NonSpecificPainService } from 'app/entities/non-specific-pain/non-specific-pain.service';

describe('Component Tests', () => {
    describe('NonSpecificPain Management Delete Component', () => {
        let comp: NonSpecificPainDeleteDialogComponent;
        let fixture: ComponentFixture<NonSpecificPainDeleteDialogComponent>;
        let service: NonSpecificPainService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [NonSpecificPainDeleteDialogComponent]
            })
                .overrideTemplate(NonSpecificPainDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NonSpecificPainDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NonSpecificPainService);
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
