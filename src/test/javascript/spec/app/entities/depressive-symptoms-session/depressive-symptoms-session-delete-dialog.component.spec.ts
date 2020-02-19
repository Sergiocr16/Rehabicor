/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RehabicorTestModule } from '../../../test.module';
import { DepressiveSymptomsSessionDeleteDialogComponent } from 'app/entities/depressive-symptoms-session/depressive-symptoms-session-delete-dialog.component';
import { DepressiveSymptomsSessionService } from 'app/entities/depressive-symptoms-session/depressive-symptoms-session.service';

describe('Component Tests', () => {
    describe('DepressiveSymptomsSession Management Delete Component', () => {
        let comp: DepressiveSymptomsSessionDeleteDialogComponent;
        let fixture: ComponentFixture<DepressiveSymptomsSessionDeleteDialogComponent>;
        let service: DepressiveSymptomsSessionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [DepressiveSymptomsSessionDeleteDialogComponent]
            })
                .overrideTemplate(DepressiveSymptomsSessionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepressiveSymptomsSessionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepressiveSymptomsSessionService);
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
