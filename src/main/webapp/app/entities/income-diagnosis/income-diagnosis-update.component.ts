import { Component, OnDestroy, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IIncomeDiagnosis, IncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';
import { IncomeDiagnosisService } from './income-diagnosis.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';
import { ModalService } from 'app/shared/util/modal.service';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';

@Component({
    selector: 'jhi-income-diagnosis-update',
    templateUrl: './income-diagnosis-update.component.html'
})
export class IncomeDiagnosisUpdateComponent implements OnInit, OnDestroy {
    isSaving: boolean;

    rehabilitationcenters: IRehabilitationCenter[];
    modalSuccessMessage;
    confirmMessage;
    title;
    incomeDiagnosis: any;

    editForm = this.fb.group({
        id: [],
        description: [null, [Validators.required]],
        deleted: [],
        rehabilitationCenterId: []
    });

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected incomeDiagnosisService: IncomeDiagnosisService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder,
        protected modal: ModalService,
        private global: GlobalVariablesService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ incomeDiagnosis }) => {
            this.updateForm(incomeDiagnosis);
            this.title = !incomeDiagnosis.id ? 'Crear un diagnóstico de ingreso' : 'Editar un diagnóstico de ingreso';
            this.modalSuccessMessage = !incomeDiagnosis.id
                ? 'Diagnóstico de ingreso creado correctamente.'
                : 'Diagnóstico de ingreso editado correctamente.';
            this.confirmMessage = !incomeDiagnosis.id ? 'new' : 'update';
            this.global.setTitle(this.title);
        });
        this.rehabilitationCenterService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRehabilitationCenter[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRehabilitationCenter[]>) => response.body)
            )
            .subscribe(
                (res: IRehabilitationCenter[]) => (this.rehabilitationcenters = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.global.enteringForm();
    }

    setInvalidForm(isSaving) {
        this.global.setFormStatus(isSaving);
    }

    ngOnDestroy() {
        this.global.leavingForm();
    }

    updateForm(incomeDiagnosis: IIncomeDiagnosis) {
        this.editForm.patchValue({
            id: incomeDiagnosis.id,
            description: incomeDiagnosis.description,
            deleted: incomeDiagnosis.deleted,
            rehabilitationCenterId: incomeDiagnosis.rehabilitationCenterId
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.modal.confirmDialog(this.confirmMessage, () => {
            this.isSaving = true;
            const incomeDiagnosis = this.createFromForm();
            if (incomeDiagnosis.id !== undefined) {
                this.subscribeToSaveResponse(this.incomeDiagnosisService.update(incomeDiagnosis));
            } else {
                this.subscribeToSaveResponse(this.incomeDiagnosisService.create(incomeDiagnosis));
            }
        });
    }

    private createFromForm(): IIncomeDiagnosis {
        return {
            ...new IncomeDiagnosis(),
            id: this.editForm.get(['id']).value,
            description: this.editForm.get(['description']).value,
            deleted: this.editForm.get(['deleted']).value,
            rehabilitationCenterId: this.editForm.get(['rehabilitationCenterId']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncomeDiagnosis>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.modal.message(this.modalSuccessMessage);
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRehabilitationCenterById(index: number, item: IRehabilitationCenter) {
        return item.id;
    }
}
