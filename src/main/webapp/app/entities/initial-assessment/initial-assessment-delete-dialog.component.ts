import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInitialAssessment } from 'app/shared/model/initial-assessment.model';
import { InitialAssessmentService } from './initial-assessment.service';

@Component({
    selector: 'jhi-initial-assessment-delete-dialog',
    templateUrl: './initial-assessment-delete-dialog.component.html'
})
export class InitialAssessmentDeleteDialogComponent {
    initialAssessment: IInitialAssessment;

    constructor(
        protected initialAssessmentService: InitialAssessmentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.initialAssessmentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'initialAssessmentListModification',
                content: 'Deleted an initialAssessment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-initial-assessment-delete-popup',
    template: ''
})
export class InitialAssessmentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ initialAssessment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InitialAssessmentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.initialAssessment = initialAssessment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/initial-assessment', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/initial-assessment', { outlets: { popup: null } }]);
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
