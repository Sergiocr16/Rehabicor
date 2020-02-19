import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFinalAssessment } from 'app/shared/model/final-assessment.model';
import { FinalAssessmentService } from './final-assessment.service';

@Component({
    selector: 'jhi-final-assessment-delete-dialog',
    templateUrl: './final-assessment-delete-dialog.component.html'
})
export class FinalAssessmentDeleteDialogComponent {
    finalAssessment: IFinalAssessment;

    constructor(
        protected finalAssessmentService: FinalAssessmentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.finalAssessmentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'finalAssessmentListModification',
                content: 'Deleted an finalAssessment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-final-assessment-delete-popup',
    template: ''
})
export class FinalAssessmentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ finalAssessment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FinalAssessmentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.finalAssessment = finalAssessment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/final-assessment', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/final-assessment', { outlets: { popup: null } }]);
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
