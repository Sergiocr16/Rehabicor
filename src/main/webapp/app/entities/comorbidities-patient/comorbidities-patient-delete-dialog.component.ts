import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComorbiditiesPatient } from 'app/shared/model/comorbidities-patient.model';
import { ComorbiditiesPatientService } from './comorbidities-patient.service';

@Component({
    selector: 'jhi-comorbidities-patient-delete-dialog',
    templateUrl: './comorbidities-patient-delete-dialog.component.html'
})
export class ComorbiditiesPatientDeleteDialogComponent {
    comorbiditiesPatient: IComorbiditiesPatient;

    constructor(
        protected comorbiditiesPatientService: ComorbiditiesPatientService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.comorbiditiesPatientService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'comorbiditiesPatientListModification',
                content: 'Deleted an comorbiditiesPatient'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-comorbidities-patient-delete-popup',
    template: ''
})
export class ComorbiditiesPatientDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ comorbiditiesPatient }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ComorbiditiesPatientDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.comorbiditiesPatient = comorbiditiesPatient;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/comorbidities-patient', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/comorbidities-patient', { outlets: { popup: null } }]);
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
