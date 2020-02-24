import { Component, OnDestroy, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRehabilitationCenter, RehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from './rehabilitation-center.service';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';
import { ModalService } from 'app/shared/util/modal.service';

@Component({
    selector: 'jhi-rehabilitation-center-update',
    templateUrl: './rehabilitation-center-update.component.html'
})
export class RehabilitationCenterUpdateComponent implements OnInit, OnDestroy {
    isSaving: boolean;
    title;
    confirmMessage;
    modalSuccessMessage;
    rehabilitationCenter: any;
    editForm = this.fb.group({
        id: [],
        name: [null, [Validators.required]],
        telephone: [],
        deleted: [],
        status: []
    });

    constructor(
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder,
        private global: GlobalVariablesService,
        private modal: ModalService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rehabilitationCenter }) => {
            this.updateForm(rehabilitationCenter);
            this.title = !rehabilitationCenter.id ? 'Crear un centro' : 'Editar un centro';
            this.modalSuccessMessage = !rehabilitationCenter.id
                ? 'Centro de rehabilitación creado correctamente.'
                : 'Centro de rehabilitación editado correctamente.';
            this.confirmMessage = !rehabilitationCenter.id ? 'new' : 'update';
            this.global.setTitle(this.title);
        });
    }

    updateForm(rehabilitationCenter: IRehabilitationCenter) {
        this.editForm.patchValue({
            id: rehabilitationCenter.id,
            name: rehabilitationCenter.name,
            telephone: rehabilitationCenter.telephone,
            deleted: rehabilitationCenter.deleted,
            status: rehabilitationCenter.status
        });
    }

    previousState() {
        window.history.back();
    }

    setInvalidForm(isSaving) {
        this.global.setFormStatus(isSaving);
    }

    ngOnDestroy() {
        this.global.leavingForm();
    }

    save() {
        this.modal.confirmDialog(this.confirmMessage, () => {
            this.isSaving = true;
            const rehabilitationCenter = this.createFromForm();
            if (rehabilitationCenter.id !== undefined) {
                this.subscribeToSaveResponse(this.rehabilitationCenterService.update(rehabilitationCenter));
            } else {
                rehabilitationCenter.deleted = false;
                this.subscribeToSaveResponse(this.rehabilitationCenterService.create(rehabilitationCenter));
            }
        });
    }

    private createFromForm(): IRehabilitationCenter {
        return {
            ...new RehabilitationCenter(),
            id: this.editForm.get(['id']).value,
            name: this.editForm.get(['name']).value,
            telephone: this.editForm.get(['telephone']).value,
            deleted: this.editForm.get(['deleted']).value,
            status: this.editForm.get(['status']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRehabilitationCenter>>) {
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
}
