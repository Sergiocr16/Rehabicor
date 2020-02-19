/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RehabicorTestModule } from '../../../test.module';
import { MayorEventDeleteDialogComponent } from 'app/entities/mayor-event/mayor-event-delete-dialog.component';
import { MayorEventService } from 'app/entities/mayor-event/mayor-event.service';

describe('Component Tests', () => {
    describe('MayorEvent Management Delete Component', () => {
        let comp: MayorEventDeleteDialogComponent;
        let fixture: ComponentFixture<MayorEventDeleteDialogComponent>;
        let service: MayorEventService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [MayorEventDeleteDialogComponent]
            })
                .overrideTemplate(MayorEventDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MayorEventDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MayorEventService);
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
