import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComorbiditie } from 'app/shared/model/comorbiditie.model';
import { ComorbiditieService } from './comorbiditie.service';

@Component({
    selector: 'jhi-comorbiditie-delete-dialog',
    templateUrl: './comorbiditie-delete-dialog.component.html'
})
export class ComorbiditieDeleteDialogComponent {
    comorbiditie: IComorbiditie;

    constructor(
        protected comorbiditieService: ComorbiditieService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.comorbiditieService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'comorbiditieListModification',
                content: 'Deleted an comorbiditie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-comorbiditie-delete-popup',
    template: ''
})
export class ComorbiditieDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ comorbiditie }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ComorbiditieDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.comorbiditie = comorbiditie;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/comorbiditie', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/comorbiditie', { outlets: { popup: null } }]);
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
