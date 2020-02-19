import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMayorEventsSession } from 'app/shared/model/mayor-events-session.model';
import { MayorEventsSessionService } from './mayor-events-session.service';

@Component({
    selector: 'jhi-mayor-events-session-delete-dialog',
    templateUrl: './mayor-events-session-delete-dialog.component.html'
})
export class MayorEventsSessionDeleteDialogComponent {
    mayorEventsSession: IMayorEventsSession;

    constructor(
        protected mayorEventsSessionService: MayorEventsSessionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mayorEventsSessionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mayorEventsSessionListModification',
                content: 'Deleted an mayorEventsSession'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mayor-events-session-delete-popup',
    template: ''
})
export class MayorEventsSessionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mayorEventsSession }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MayorEventsSessionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.mayorEventsSession = mayorEventsSession;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/mayor-events-session', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/mayor-events-session', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
