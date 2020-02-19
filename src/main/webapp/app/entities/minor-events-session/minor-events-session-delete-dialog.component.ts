import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMinorEventsSession } from 'app/shared/model/minor-events-session.model';
import { MinorEventsSessionService } from './minor-events-session.service';

@Component({
    selector: 'jhi-minor-events-session-delete-dialog',
    templateUrl: './minor-events-session-delete-dialog.component.html'
})
export class MinorEventsSessionDeleteDialogComponent {
    minorEventsSession: IMinorEventsSession;

    constructor(
        protected minorEventsSessionService: MinorEventsSessionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.minorEventsSessionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'minorEventsSessionListModification',
                content: 'Deleted an minorEventsSession'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-minor-events-session-delete-popup',
    template: ''
})
export class MinorEventsSessionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ minorEventsSession }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MinorEventsSessionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.minorEventsSession = minorEventsSession;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/minor-events-session', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/minor-events-session', { outlets: { popup: null } }]);
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
