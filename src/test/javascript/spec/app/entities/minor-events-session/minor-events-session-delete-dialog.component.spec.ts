/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RehabicorTestModule } from '../../../test.module';
import { MinorEventsSessionDeleteDialogComponent } from 'app/entities/minor-events-session/minor-events-session-delete-dialog.component';
import { MinorEventsSessionService } from 'app/entities/minor-events-session/minor-events-session.service';

describe('Component Tests', () => {
    describe('MinorEventsSession Management Delete Component', () => {
        let comp: MinorEventsSessionDeleteDialogComponent;
        let fixture: ComponentFixture<MinorEventsSessionDeleteDialogComponent>;
        let service: MinorEventsSessionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [MinorEventsSessionDeleteDialogComponent]
            })
                .overrideTemplate(MinorEventsSessionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MinorEventsSessionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MinorEventsSessionService);
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
