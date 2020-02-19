import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMayorEvent } from 'app/shared/model/mayor-event.model';
import { MayorEventService } from './mayor-event.service';

@Component({
    selector: 'jhi-mayor-event-delete-dialog',
    templateUrl: './mayor-event-delete-dialog.component.html'
})
export class MayorEventDeleteDialogComponent {
    mayorEvent: IMayorEvent;

    constructor(
        protected mayorEventService: MayorEventService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mayorEventService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mayorEventListModification',
                content: 'Deleted an mayorEvent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mayor-event-delete-popup',
    template: ''
})
export class MayorEventDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mayorEvent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MayorEventDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.mayorEvent = mayorEvent;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/mayor-event', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/mayor-event', { outlets: { popup: null } }]);
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
