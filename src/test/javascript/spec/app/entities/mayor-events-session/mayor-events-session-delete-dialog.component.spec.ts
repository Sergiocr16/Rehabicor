/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RehabicorTestModule } from '../../../test.module';
import { MayorEventsSessionDeleteDialogComponent } from 'app/entities/mayor-events-session/mayor-events-session-delete-dialog.component';
import { MayorEventsSessionService } from 'app/entities/mayor-events-session/mayor-events-session.service';

describe('Component Tests', () => {
    describe('MayorEventsSession Management Delete Component', () => {
        let comp: MayorEventsSessionDeleteDialogComponent;
        let fixture: ComponentFixture<MayorEventsSessionDeleteDialogComponent>;
        let service: MayorEventsSessionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [MayorEventsSessionDeleteDialogComponent]
            })
                .overrideTemplate(MayorEventsSessionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MayorEventsSessionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MayorEventsSessionService);
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
