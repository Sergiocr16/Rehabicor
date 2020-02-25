import { Component, OnDestroy, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMinorEvent, MinorEvent } from 'app/shared/model/minor-event.model';
import { MinorEventService } from './minor-event.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';
import { ModalService } from 'app/shared/util/modal.service';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';

@Component({
    selector: 'jhi-minor-event-update',
    templateUrl: './minor-event-update.component.html'
})
export class MinorEventUpdateComponent implements OnInit, OnDestroy {
    isSaving: boolean;
    title;
    modalSuccessMessage;
    minorEvent: MinorEvent;
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
        protected minorEventService: MinorEventService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute,
        protected modal: ModalService,
        private global: GlobalVariablesService,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ minorEvent }) => {
            this.updateForm(minorEvent);
            this.confirmMessage = !minorEvent.id ? 'new' : 'update';
            this.title = !minorEvent.id ? 'Crear un evento menor' : 'Editar  un evento menor';
            this.modalSuccessMessage = !minorEvent.id ? 'Evento menor creado correctamente.' : 'Evento menor editado correctamente.';
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

    ngOnDestroy() {
        this.global.leavingForm();
    }

    updateForm(minorEvent: IMinorEvent) {
        this.editForm.patchValue({
            id: minorEvent.id,
            description: minorEvent.description,
            code: minorEvent.code,
            deleted: minorEvent.deleted,
            rehabilitationCenterId: minorEvent.rehabilitationCenterId
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.modal.confirmDialog(this.confirmMessage, () => {
            this.isSaving = true;
            const minorEvent = this.createFromForm();
            if (minorEvent.id !== undefined) {
                this.subscribeToSaveResponse(this.minorEventService.update(minorEvent));
            } else {
                this.subscribeToSaveResponse(this.minorEventService.create(minorEvent));
            }
        });
    }

    private createFromForm(): IMinorEvent {
        return {
            ...new MinorEvent(),
            id: this.editForm.get(['id']).value,
            description: this.editForm.get(['description']).value,
            code: this.editForm.get(['code']).value,
            deleted: this.editForm.get(['deleted']).value,
            rehabilitationCenterId: this.editForm.get(['rehabilitationCenterId']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMinorEvent>>) {
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
