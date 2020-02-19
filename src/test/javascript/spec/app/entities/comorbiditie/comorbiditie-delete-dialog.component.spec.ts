/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RehabicorTestModule } from '../../../test.module';
import { ComorbiditieDeleteDialogComponent } from 'app/entities/comorbiditie/comorbiditie-delete-dialog.component';
import { ComorbiditieService } from 'app/entities/comorbiditie/comorbiditie.service';

describe('Component Tests', () => {
    describe('Comorbiditie Management Delete Component', () => {
        let comp: ComorbiditieDeleteDialogComponent;
        let fixture: ComponentFixture<ComorbiditieDeleteDialogComponent>;
        let service: ComorbiditieService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [ComorbiditieDeleteDialogComponent]
            })
                .overrideTemplate(ComorbiditieDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ComorbiditieDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComorbiditieService);
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
