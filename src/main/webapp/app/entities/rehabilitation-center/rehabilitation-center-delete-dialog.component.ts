import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from './rehabilitation-center.service';

@Component({
    selector: 'jhi-rehabilitation-center-delete-dialog',
    templateUrl: './rehabilitation-center-delete-dialog.component.html'
})
export class RehabilitationCenterDeleteDialogComponent {
    rehabilitationCenter: IRehabilitationCenter;

    constructor(
        protected rehabilitationCenterService: RehabilitationCenterService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rehabilitationCenterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rehabilitationCenterListModification',
                content: 'Deleted an rehabilitationCenter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rehabilitation-center-delete-popup',
    template: ''
})
export class RehabilitationCenterDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rehabilitationCenter }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RehabilitationCenterDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.rehabilitationCenter = rehabilitationCenter;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/rehabilitation-center', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/rehabilitation-center', { outlets: { popup: null } }]);
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
