import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepressiveSymptomsSession } from 'app/shared/model/depressive-symptoms-session.model';
import { DepressiveSymptomsSessionService } from './depressive-symptoms-session.service';

@Component({
    selector: 'jhi-depressive-symptoms-session-delete-dialog',
    templateUrl: './depressive-symptoms-session-delete-dialog.component.html'
})
export class DepressiveSymptomsSessionDeleteDialogComponent {
    depressiveSymptomsSession: IDepressiveSymptomsSession;

    constructor(
        protected depressiveSymptomsSessionService: DepressiveSymptomsSessionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.depressiveSymptomsSessionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'depressiveSymptomsSessionListModification',
                content: 'Deleted an depressiveSymptomsSession'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-depressive-symptoms-session-delete-popup',
    template: ''
})
export class DepressiveSymptomsSessionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depressiveSymptomsSession }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DepressiveSymptomsSessionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.depressiveSymptomsSession = depressiveSymptomsSession;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/depressive-symptoms-session', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/depressive-symptoms-session', { outlets: { popup: null } }]);
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
