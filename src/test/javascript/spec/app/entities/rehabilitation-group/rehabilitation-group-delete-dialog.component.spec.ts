/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RehabicorTestModule } from '../../../test.module';
import { RehabilitationGroupDeleteDialogComponent } from 'app/entities/rehabilitation-group/rehabilitation-group-delete-dialog.component';
import { RehabilitationGroupService } from 'app/entities/rehabilitation-group/rehabilitation-group.service';

describe('Component Tests', () => {
    describe('RehabilitationGroup Management Delete Component', () => {
        let comp: RehabilitationGroupDeleteDialogComponent;
        let fixture: ComponentFixture<RehabilitationGroupDeleteDialogComponent>;
        let service: RehabilitationGroupService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [RehabilitationGroupDeleteDialogComponent]
            })
                .overrideTemplate(RehabilitationGroupDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RehabilitationGroupDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RehabilitationGroupService);
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
