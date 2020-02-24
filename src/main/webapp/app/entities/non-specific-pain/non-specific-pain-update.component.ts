import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INonSpecificPain, NonSpecificPain } from 'app/shared/model/non-specific-pain.model';
import { NonSpecificPainService } from './non-specific-pain.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';
import { ModalService } from 'app/shared/util/modal.service';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';

@Component({
    selector: 'jhi-non-specific-pain-update',
    templateUrl: './non-specific-pain-update.component.html'
})
export class NonSpecificPainUpdateComponent implements OnInit {
    isSaving: boolean;
    title;
    nonSpecificPain: NonSpecificPain;
    modalSuccessMessage;
    rehabilitationcenters: IRehabilitationCenter[];
    confirmMessage;
    editForm = this.fb.group({
        id: [],
        description: [null, [Validators.required]],
        code: [],
        deleted: [],
        rehabilitationCenterId: []
    });

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected nonSpecificPainService: NonSpecificPainService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute,
        protected modal: ModalService,
        private global: GlobalVariablesService,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ nonSpecificPain }) => {
            this.updateForm(nonSpecificPain);
            this.confirmMessage = !nonSpecificPain.id ? 'new' : 'update';
            this.title = !nonSpecificPain.id ? 'Crear un dolor no identificado ' : 'Editar un dolor no identificado';
            this.modalSuccessMessage = !nonSpecificPain.id
                ? 'Dolor agudo no específico creado correctamente.'
                : 'Dolor agudo no específico editado correctamente.';
            this.global.setTitle(this.title);
        });
        this.global.enteringForm();
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
    }

    setInvalidForm(isSaving) {
        this.global.setFormStatus(isSaving);
    }

    updateForm(nonSpecificPain: INonSpecificPain) {
        this.editForm.patchValue({
            id: nonSpecificPain.id,
            description: nonSpecificPain.description,
            code: nonSpecificPain.code,
            deleted: nonSpecificPain.deleted,
            rehabilitationCenterId: nonSpecificPain.rehabilitationCenterId
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.modal.confirmDialog(this.confirmMessage, () => {
            this.isSaving = true;
            const nonSpecificPain = this.createFromForm();
            if (nonSpecificPain.id !== undefined) {
                this.subscribeToSaveResponse(this.nonSpecificPainService.update(nonSpecificPain));
            } else {
                this.subscribeToSaveResponse(this.nonSpecificPainService.create(nonSpecificPain));
            }
        });
    }

    private createFromForm(): INonSpecificPain {
        return {
            ...new NonSpecificPain(),
            id: this.editForm.get(['id']).value,
            description: this.editForm.get(['description']).value,
            code: this.editForm.get(['code']).value,
            deleted: this.editForm.get(['deleted']).value,
            rehabilitationCenterId: this.editForm.get(['rehabilitationCenterId']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<INonSpecificPain>>) {
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
