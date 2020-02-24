import { Component, OnDestroy, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFinalAssessment, FinalAssessment } from 'app/shared/model/final-assessment.model';
import { FinalAssessmentService } from './final-assessment.service';
import { IPatient, Patient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';
import { ModalService } from 'app/shared/util/modal.service';
import * as moment from 'moment';

@Component({
    selector: 'jhi-final-assessment-update',
    templateUrl: './final-assessment-update.component.html'
})
export class FinalAssessmentUpdateComponent implements OnInit, OnDestroy {
    isSaving: boolean;

    patients: IPatient[];
    title;
    modalConfirm;
    modalSuccessMessage;
    patient;
    patientId;
    smokingOptions = ['Activo', 'Inactivo'];
    isReevaluation;
    finalAssessment: FinalAssessment;

    editForm = this.fb.group({
        id: [],
        smoking: [],
        weight: [],
        size: [],
        iMC: [],
        hbiac: [],
        baselineFunctionalCapacity: [],
        lDL: [],
        hDL: [],
        cardiovascularRisk: [],
        isWorking: [false],
        deceased: [false],
        abandonment: [false],
        abandonmentMedicCause: [false],
        hospitalized: [false],
        deleted: [],
        patientId: []
    });

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected finalAssessmentService: FinalAssessmentService,
        protected patientService: PatientService,
        protected activatedRoute: ActivatedRoute,
        protected global: GlobalVariablesService,
        protected route: ActivatedRoute,
        protected modal: ModalService,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ finalAssessment }) => {
            this.updateForm(finalAssessment);
            this.title = finalAssessment.id == null ? 'Evaluación final' : 'Editar paciente';
            this.modalConfirm = finalAssessment.id == null ? 'new' : 'update';
            this.modalSuccessMessage =
                finalAssessment.id == null ? 'Evaluación final creada correctamente.' : 'Evaluación final editada correctamente.';
        });
        this.patientId = this.route.snapshot.params['patientId'];
        this.patientService
            .find(this.patientId)
            .pipe(
                filter((response: HttpResponse<Patient>) => response.ok),
                map((patient: HttpResponse<Patient>) => patient.body)
            )
            .subscribe(patient => {
                this.patient = patient;
                this.isReevaluation = this.route.routeConfig.path.split('/')[1] === 'new-reevaluation';
                this.title = 'Paciente ' + this.patient.code + ', ' + (this.isReevaluation ? 'Reevaluación final' : 'Evaluación final');
                this.global.setTitle(this.title);
            });
        this.global.enteringForm();
    }
    calculateIMC() {
        this.editForm
            .get('iMC')
            .setValue(parseFloat(this.editForm.get('weight').value / Math.pow(this.editForm.get('size').value, 2) + '').toFixed(3));
    }
    setInvalidForm(isSaving) {
        this.global.setFormStatus(isSaving);
    }
    updateForm(finalAssessment: IFinalAssessment) {
        this.editForm.patchValue({
            id: finalAssessment.id,
            smoking: this.patient.smoking,
            weight: finalAssessment.weight,
            size: finalAssessment.size,
            iMC: finalAssessment.iMC,
            hbiac: finalAssessment.hbiac,
            baselineFunctionalCapacity: finalAssessment.baselineFunctionalCapacity,
            lDL: finalAssessment.lDL,
            hDL: finalAssessment.hDL,
            cardiovascularRisk: finalAssessment.cardiovascularRisk,
            isWorking: finalAssessment.isWorking,
            deceased: finalAssessment.deceased,
            abandonment: finalAssessment.abandonment,
            abandonmentMedicCause: finalAssessment.abandonmentMedicCause,
            hospitalized: finalAssessment.hospitalized,
            deleted: finalAssessment.deleted,
            patientId: this.patient.id
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.modal.confirmCustomDialog(
            '¿Está seguro que desea registrar la evaluación final?',
            'Una vez registrada no podrá modificarse',
            () => {
                this.isSaving = true;
                const finalAssessment = this.createFromForm();
                if (finalAssessment.id !== null) {
                    this.subscribeToSaveResponse(this.finalAssessmentService.update(finalAssessment));
                } else {
                    finalAssessment.reevaluation = this.isReevaluation;
                    finalAssessment.executionDate = moment(new Date());
                    this.subscribeToSaveResponse(this.finalAssessmentService.create(finalAssessment));
                }
            }
        );
    }

    private createFromForm(): IFinalAssessment {
        return {
            ...new FinalAssessment(),
            id: this.editForm.get(['id']).value,
            smoking: this.editForm.get(['smoking']).value,
            weight: this.editForm.get(['weight']).value,
            size: this.editForm.get(['size']).value,
            iMC: this.editForm.get(['iMC']).value,
            hbiac: this.editForm.get(['hbiac']).value,
            baselineFunctionalCapacity: this.editForm.get(['baselineFunctionalCapacity']).value,
            lDL: this.editForm.get(['lDL']).value,
            hDL: this.editForm.get(['hDL']).value,
            cardiovascularRisk: this.editForm.get(['cardiovascularRisk']).value,
            isWorking: this.editForm.get(['isWorking']).value,
            deceased: this.editForm.get(['deceased']).value,
            abandonment: this.editForm.get(['abandonment']).value,
            abandonmentMedicCause: this.editForm.get(['abandonmentMedicCause']).value === '1',
            hospitalized: this.editForm.get(['abandonmentMedicCause']).value === '2',
            deleted: this.editForm.get(['deleted']).value,
            patientId: this.patient.id
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFinalAssessment>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.patient.rehabStatus = this.isReevaluation ? 3 : 2;
        this.subscribeToSaveResponsePatientt(this.patientService.update(this.patient));
    }
    protected subscribeToSaveResponsePatientt(result: Observable<HttpResponse<IFinalAssessment>>) {
        result.subscribe(() => this.onSaveSuccessPatient(), () => this.onSaveError());
    }

    protected onSaveSuccessPatient() {
        this.isSaving = false;
        this.previousState();
        this.modal.message('Se ha registrado la evaluación correctamente.');
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
    ngOnDestroy() {
        this.global.leavingForm();
    }
}
