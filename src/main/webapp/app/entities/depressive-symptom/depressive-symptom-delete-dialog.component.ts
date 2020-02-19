import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepressiveSymptom } from 'app/shared/model/depressive-symptom.model';
import { DepressiveSymptomService } from './depressive-symptom.service';

@Component({
    selector: 'jhi-depressive-symptom-delete-dialog',
    templateUrl: './depressive-symptom-delete-dialog.component.html'
})
export class DepressiveSymptomDeleteDialogComponent {
    depressiveSymptom: IDepressiveSymptom;

    constructor(
        protected depressiveSymptomService: DepressiveSymptomService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.depressiveSymptomService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'depressiveSymptomListModification',
                content: 'Deleted an depressiveSymptom'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-depressive-symptom-delete-popup',
    template: ''
})
export class DepressiveSymptomDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depressiveSymptom }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DepressiveSymptomDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.depressiveSymptom = depressiveSymptom;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/depressive-symptom', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/depressive-symptom', { outlets: { popup: null } }]);
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
