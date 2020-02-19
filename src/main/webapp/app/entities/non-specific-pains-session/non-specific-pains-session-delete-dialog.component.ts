import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INonSpecificPainsSession } from 'app/shared/model/non-specific-pains-session.model';
import { NonSpecificPainsSessionService } from './non-specific-pains-session.service';

@Component({
    selector: 'jhi-non-specific-pains-session-delete-dialog',
    templateUrl: './non-specific-pains-session-delete-dialog.component.html'
})
export class NonSpecificPainsSessionDeleteDialogComponent {
    nonSpecificPainsSession: INonSpecificPainsSession;

    constructor(
        protected nonSpecificPainsSessionService: NonSpecificPainsSessionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nonSpecificPainsSessionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'nonSpecificPainsSessionListModification',
                content: 'Deleted an nonSpecificPainsSession'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-non-specific-pains-session-delete-popup',
    template: ''
})
export class NonSpecificPainsSessionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ nonSpecificPainsSession }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NonSpecificPainsSessionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.nonSpecificPainsSession = nonSpecificPainsSession;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/non-specific-pains-session', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/non-specific-pains-session', { outlets: { popup: null } }]);
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
