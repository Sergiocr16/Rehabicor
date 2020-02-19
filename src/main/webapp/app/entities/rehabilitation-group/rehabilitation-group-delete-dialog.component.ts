import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';
import { RehabilitationGroupService } from './rehabilitation-group.service';

@Component({
    selector: 'jhi-rehabilitation-group-delete-dialog',
    templateUrl: './rehabilitation-group-delete-dialog.component.html'
})
export class RehabilitationGroupDeleteDialogComponent {
    rehabilitationGroup: IRehabilitationGroup;

    constructor(
        protected rehabilitationGroupService: RehabilitationGroupService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rehabilitationGroupService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rehabilitationGroupListModification',
                content: 'Deleted an rehabilitationGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rehabilitation-group-delete-popup',
    template: ''
})
export class RehabilitationGroupDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rehabilitationGroup }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RehabilitationGroupDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.rehabilitationGroup = rehabilitationGroup;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/rehabilitation-group', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/rehabilitation-group', { outlets: { popup: null } }]);
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
