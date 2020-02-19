import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';
import { IncomeDiagnosisService } from './income-diagnosis.service';

@Component({
    selector: 'jhi-income-diagnosis-delete-dialog',
    templateUrl: './income-diagnosis-delete-dialog.component.html'
})
export class IncomeDiagnosisDeleteDialogComponent {
    incomeDiagnosis: IIncomeDiagnosis;

    constructor(
        protected incomeDiagnosisService: IncomeDiagnosisService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.incomeDiagnosisService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'incomeDiagnosisListModification',
                content: 'Deleted an incomeDiagnosis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-income-diagnosis-delete-popup',
    template: ''
})
export class IncomeDiagnosisDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incomeDiagnosis }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IncomeDiagnosisDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.incomeDiagnosis = incomeDiagnosis;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/income-diagnosis', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/income-diagnosis', { outlets: { popup: null } }]);
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
