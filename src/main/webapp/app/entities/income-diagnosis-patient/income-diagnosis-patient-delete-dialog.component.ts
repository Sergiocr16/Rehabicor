import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncomeDiagnosisPatient } from 'app/shared/model/income-diagnosis-patient.model';
import { IncomeDiagnosisPatientService } from './income-diagnosis-patient.service';

@Component({
    selector: 'jhi-income-diagnosis-patient-delete-dialog',
    templateUrl: './income-diagnosis-patient-delete-dialog.component.html'
})
export class IncomeDiagnosisPatientDeleteDialogComponent {
    incomeDiagnosisPatient: IIncomeDiagnosisPatient;

    constructor(
        protected incomeDiagnosisPatientService: IncomeDiagnosisPatientService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.incomeDiagnosisPatientService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'incomeDiagnosisPatientListModification',
                content: 'Deleted an incomeDiagnosisPatient'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-income-diagnosis-patient-delete-popup',
    template: ''
})
export class IncomeDiagnosisPatientDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incomeDiagnosisPatient }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IncomeDiagnosisPatientDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.incomeDiagnosisPatient = incomeDiagnosisPatient;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/income-diagnosis-patient', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/income-diagnosis-patient', { outlets: { popup: null } }]);
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
