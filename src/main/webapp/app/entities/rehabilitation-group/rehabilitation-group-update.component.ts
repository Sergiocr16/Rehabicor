import { Component, OnDestroy, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRehabilitationGroup, RehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';
import { RehabilitationGroupService } from './rehabilitation-group.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';
import { ModalService } from 'app/shared/util/modal.service';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';

@Component({
    selector: 'jhi-rehabilitation-group-update',
    templateUrl: './rehabilitation-group-update.component.html'
})
export class RehabilitationGroupUpdateComponent implements OnInit, OnDestroy {
    isSaving: boolean;
    rehabilitationGroup: RehabilitationGroup;
    patients: IPatient[];
    title;
    modalSuccessMessage;
    rehabilitationcenters: IRehabilitationCenter[];
    modalConfirm;
    editForm = this.fb.group({
        id: [],
        name: [null, [Validators.required]],
        creationDate: [],
        programStatus: [],
        deleted: [],
        patients: [],
        rehabilitationCenterId: []
    });
    page = 0;
    itemsPerPage = 1000;
    programStatusArray = [{ d: 'Sin iniciar', v: 0 }, { d: 'En proceso', v: 1 }, { d: 'Finalizado', v: 2 }];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rehabilitationGroupService: RehabilitationGroupService,
        protected patientService: PatientService,
        protected modal: ModalService,
        private global: GlobalVariablesService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rehabilitationGroup }) => {
            this.updateForm(rehabilitationGroup);
            this.title = rehabilitationGroup.id == null ? 'Crear grupo' : 'Editar grupo';
            this.modalConfirm = rehabilitationGroup.id == null ? 'new' : 'update';
            this.modalSuccessMessage = rehabilitationGroup.id == null ? 'Grupo creado correctamente.' : 'Grupo editado correctamente.';
            this.global.setTitle(this.title);
        });
        this.global.enteringForm();

        this.patientService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IPatient[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPatient[]>) => response.body)
            )
            .subscribe((res: IPatient[]) => (this.patients = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    updateForm(rehabilitationGroup: IRehabilitationGroup) {
        this.editForm.patchValue({
            id: rehabilitationGroup.id,
            name: rehabilitationGroup.name,
            creationDate: rehabilitationGroup.creationDate != null ? rehabilitationGroup.creationDate.format(DATE_TIME_FORMAT) : null,
            programStatus: rehabilitationGroup.programStatus,
            deleted: rehabilitationGroup.deleted,
            patients: rehabilitationGroup.patients,
            rehabilitationCenterId: rehabilitationGroup.rehabilitationCenterId
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.modal.confirmDialog(this.modalConfirm, () => {
            this.isSaving = true;
            const rehabilitationGroup = this.createFromForm();
            rehabilitationGroup.rehabilitationCenterId = this.global.rehabCenter;
            if (rehabilitationGroup.id !== undefined) {
                this.subscribeToSaveResponse(this.rehabilitationGroupService.update(rehabilitationGroup));
            } else {
                rehabilitationGroup.programStatus = 1;
                rehabilitationGroup.creationDate = moment(new Date());
                this.subscribeToSaveResponse(this.rehabilitationGroupService.create(rehabilitationGroup));
            }
        });
    }

    setInvalidForm(isSaving) {
        this.global.setFormStatus(isSaving);
    }

    private createFromForm(): IRehabilitationGroup {
        return {
            ...new RehabilitationGroup(),
            id: this.editForm.get(['id']).value,
            name: this.editForm.get(['name']).value,
            creationDate:
                this.editForm.get(['creationDate']).value != null
                    ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT)
                    : undefined,
            programStatus: this.editForm.get(['programStatus']).value,
            patients: this.editForm.get(['patients']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRehabilitationGroup>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPatientById(index: number, item: IPatient) {
        return item.id;
    }

    trackRehabilitationCenterById(index: number, item: IRehabilitationCenter) {
        return item.id;
    }

    getSelected(selectedVals: any[], option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }

    ngOnDestroy() {
        this.global.leavingForm();
    }
}
