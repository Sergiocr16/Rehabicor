import { Component, OnDestroy, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDepressiveSymptom, DepressiveSymptom } from 'app/shared/model/depressive-symptom.model';
import { DepressiveSymptomService } from './depressive-symptom.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';
import { ModalService } from 'app/shared/util/modal.service';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';

@Component({
    selector: 'jhi-depressive-symptom-update',
    templateUrl: './depressive-symptom-update.component.html'
})
export class DepressiveSymptomUpdateComponent implements OnInit, OnDestroy {
    isSaving: boolean;
    title;
    modalSuccessMessage;
    depressiveSymptom: DepressiveSymptom;
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
        protected depressiveSymptomService: DepressiveSymptomService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute,
        protected modal: ModalService,
        private global: GlobalVariablesService,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ depressiveSymptom }) => {
            this.updateForm(depressiveSymptom);

            this.title = !depressiveSymptom.id ? 'Crear un síntoma depresivo menor' : 'Editar un síntoma depresivo menor';
            this.modalSuccessMessage = !depressiveSymptom.id
                ? 'Síntoma depresivo creado correctamente.'
                : 'Síntoma depresivo editado correctamente.';
            this.confirmMessage = !depressiveSymptom.id ? 'new' : 'update';
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

    updateForm(depressiveSymptom: IDepressiveSymptom) {
        this.editForm.patchValue({
            id: depressiveSymptom.id,
            description: depressiveSymptom.description,
            code: depressiveSymptom.code,
            deleted: depressiveSymptom.deleted,
            rehabilitationCenterId: depressiveSymptom.rehabilitationCenterId
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.modal.confirmDialog(this.confirmMessage, () => {
            this.isSaving = true;
            const depressiveSymptom = this.createFromForm();
            if (depressiveSymptom.id !== undefined) {
                this.subscribeToSaveResponse(this.depressiveSymptomService.update(depressiveSymptom));
            } else {
                this.subscribeToSaveResponse(this.depressiveSymptomService.create(depressiveSymptom));
            }
        });
    }

    private createFromForm(): IDepressiveSymptom {
        return {
            ...new DepressiveSymptom(),
            id: this.editForm.get(['id']).value,
            description: this.editForm.get(['description']).value,
            code: this.editForm.get(['code']).value,
            deleted: this.editForm.get(['deleted']).value,
            rehabilitationCenterId: this.editForm.get(['rehabilitationCenterId']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepressiveSymptom>>) {
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
    ngOnDestroy() {
        this.global.leavingForm();
    }
}
