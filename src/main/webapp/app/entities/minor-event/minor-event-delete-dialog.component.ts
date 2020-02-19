import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMinorEvent } from 'app/shared/model/minor-event.model';
import { MinorEventService } from './minor-event.service';

@Component({
    selector: 'jhi-minor-event-delete-dialog',
    templateUrl: './minor-event-delete-dialog.component.html'
})
export class MinorEventDeleteDialogComponent {
    minorEvent: IMinorEvent;

    constructor(
        protected minorEventService: MinorEventService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.minorEventService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'minorEventListModification',
                content: 'Deleted an minorEvent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-minor-event-delete-popup',
    template: ''
})
export class MinorEventDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ minorEvent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MinorEventDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.minorEvent = minorEvent;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/minor-event', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/minor-event', { outlets: { popup: null } }]);
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
