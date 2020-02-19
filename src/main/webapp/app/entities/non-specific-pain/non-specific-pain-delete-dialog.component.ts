import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INonSpecificPain } from 'app/shared/model/non-specific-pain.model';
import { NonSpecificPainService } from './non-specific-pain.service';

@Component({
    selector: 'jhi-non-specific-pain-delete-dialog',
    templateUrl: './non-specific-pain-delete-dialog.component.html'
})
export class NonSpecificPainDeleteDialogComponent {
    nonSpecificPain: INonSpecificPain;

    constructor(
        protected nonSpecificPainService: NonSpecificPainService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nonSpecificPainService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'nonSpecificPainListModification',
                content: 'Deleted an nonSpecificPain'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-non-specific-pain-delete-popup',
    template: ''
})
export class NonSpecificPainDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ nonSpecificPain }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NonSpecificPainDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.nonSpecificPain = nonSpecificPain;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/non-specific-pain', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/non-specific-pain', { outlets: { popup: null } }]);
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
