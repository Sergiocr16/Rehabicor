import { Component, OnDestroy, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
// import * as moment from 'moment';
// import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPatient, Patient } from 'app/shared/model/patient.model';
import { PatientService } from './patient.service';
import { IRehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';
import { ModalService } from 'app/shared/util/modal.service';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';
import { IIncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';
import { IncomeDiagnosisService } from 'app/entities/income-diagnosis/income-diagnosis.service';
import { ComorbiditieService } from 'app/entities/comorbiditie/comorbiditie.service';
import { InitialAssessmentService } from 'app/entities/initial-assessment/initial-assessment.service';
import { IInitialAssessment, InitialAssessment } from 'app/shared/model/initial-assessment.model';
import { ComorbiditiesPatientService } from 'app/entities/comorbidities-patient/comorbidities-patient.service';
import { IncomeDiagnosisPatientService } from 'app/entities/income-diagnosis-patient/income-diagnosis-patient.service';
import { IncomeDiagnosisPatient } from 'app/shared/model/income-diagnosis-patient.model';
import { ComorbiditiesPatient } from 'app/shared/model/comorbidities-patient.model';
import { IComorbiditie } from 'app/shared/model/comorbiditie.model';

@Component({
    selector: 'jhi-patient-update',
    templateUrl: './patient-update.component.html'
})
export class PatientUpdateComponent implements OnInit, OnDestroy {
    isSaving: boolean;
    title;
    modalSuccessMessage;
    rehabilitationgroups: IRehabilitationGroup[];
    incomeDiagnoses = [];
    comorbidities = [];
    comorbiditiesDisplay = [];
    itemsPerPage = 1000;
    page = 0;
    imc = 0;
    patient;
    sexArray = ['Masculino', 'Femenino'];
    smokingOptions = ['Activo', 'Inactivo'];
    cardiovascularRiskOptions = ['Alto', 'Moderado', 'Bajo'];
    scholarshipArray = [
        'Ninguna',
        'Primaria Incompleta',
        'Primaria completa',
        'Secundaria Incompleta',
        'Secundaria completa',
        'Universitaria Incompleta',
        'Universitaria completa',
        'Parauniversitaria'
    ];
    ocupationArray = ['Ama de casa', 'Asalariado', 'Desempleado'];
    modalConfirm;
    totalSaved = 0;
    totaltoSave = 0;
    initialInfoForm = this.fb.group({
        id: [],
        code: [null, [Validators.required]],
        age: [null, [Validators.required]],
        sex: [[], [Validators.required]],
        ocupation: [[], [Validators.required]],
        scholarship: [[], [Validators.required]],
        lastEventOcurred: [null, [Validators.required]],
        incomeDiagnoses: [[]],
        deceased: [],
        abandonment: [],
        abandonmentMedicCause: [],
        rehabStatus: [],
        sessionNumber: [],
        deleted: []
    });

    diagnosisForm = this.fb.group({
        id: [],
        smoking: [[], [Validators.required]],
        incomeDiagnoses: [[]],
        comorbidities: [[]],
        cardiovascularRisk: [[], [Validators.required]]
    });

    measuresForm = this.fb.group({
        weight: [0, [Validators.required]],
        size: [0, [Validators.required]],
        iMC: [null, [Validators.required]],
        hbiac: [null, [Validators.required]],
        baselineFunctionalCapacity: [null, [Validators.required]],
        lDL: [null, [Validators.required]],
        hDL: [null, [Validators.required]]
    });

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected patientService: PatientService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder,
        protected modal: ModalService,
        private global: GlobalVariablesService,
        protected incomeDiagnosisService: IncomeDiagnosisService,
        protected comorbiditieService: ComorbiditieService,
        protected initialAssessmentService: InitialAssessmentService,
        protected comorbiditiesPatientService: ComorbiditiesPatientService,
        protected incomeDiagnosisPatientService: IncomeDiagnosisPatientService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ patient }) => {
            this.patient = patient;
            this.updateFormInitialInfo(patient);
            this.title = patient.id == null ? 'Crear paciente' : 'Editar paciente';
            this.modalConfirm = patient.id == null ? 'new' : 'update';
            this.modalSuccessMessage = patient.id == null ? 'Paciente creado correctamente.' : 'Paciente editado correctamente.';
            this.global.setTitle(this.title);
            // if (patient.id == null) {
            this.loadDiagnosis();
            this.loadComorbidities();
            // }
        });
        this.global.enteringForm();
        // this.rehabilitationGroupService
        //   .query()
        //   .pipe(
        //     filter((mayBeOk: HttpResponse<IRehabilitationGroup[]>) => mayBeOk.ok),
        //     map((response: HttpResponse<IRehabilitationGroup[]>) => response.body)
        //   )
        //   .subscribe((res: IRehabilitationGroup[]) => (this.rehabilitationgroups = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    calculateIMC() {
        this.measuresForm
            .get('iMC')
            .setValue(parseFloat(this.measuresForm.get('weight').value / Math.pow(this.measuresForm.get('size').value, 2) + '').toFixed(3));
    }
    loadDiagnosis() {
        this.incomeDiagnosisService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                rehabilitationId: this.global.rehabCenter
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IIncomeDiagnosis[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIncomeDiagnosis[]>) => response.body)
            )
            .subscribe((res: IIncomeDiagnosis[]) => this.formatInconeDiagnoses(res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    loadComorbidities() {
        this.comorbiditieService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                rehabilitationId: this.global.rehabCenter
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IIncomeDiagnosis[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIncomeDiagnosis[]>) => response.body)
            )
            .subscribe((res: IIncomeDiagnosis[]) => this.formatComorbidities(res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    setInvalidForm(isSaving) {
        this.global.setFormStatus(isSaving);
    }

    updateForm(patient: IPatient) {}

    formatInconeDiagnoses(incomeDiagnoses) {
        for (const incomeDiagnose of incomeDiagnoses) {
            incomeDiagnose.checked = false;
            this.incomeDiagnoses.push(incomeDiagnose);
        }
    }

    formatComorbidities(comorbidities) {
        for (const comorbiditie of comorbidities) {
            comorbiditie.checked = false;
            this.comorbidities.push(comorbiditie);
        }
    }

    updateFormInitialInfo(patient: IPatient) {
        this.initialInfoForm.patchValue({
            id: patient.id,
            code: patient.code,
            age: patient.age,
            sex: patient.sex,
            ocupation: patient.ocupation,
            scholarship: patient.scholarship,
            lastEventOcurred: patient.lastEventOcurred,
            deceased: patient.deceased,
            abandonment: patient.abandonment,
            abandonmentMedicCause: patient.abandonmentMedicCause,
            rehabStatus: patient.rehabStatus,
            sessionNumber: patient.sessionNumber,
            deleted: patient.deleted
        });
        this.updateFormDiagnosis(patient);
    }

    updateFormDiagnosis(patient: IPatient) {
        if (patient.id) {
            this.initialAssessmentService
                .findByPatient(patient.id)
                .pipe(
                    filter((response: HttpResponse<InitialAssessment>) => response.ok),
                    map((initialAssessment: HttpResponse<InitialAssessment>) => initialAssessment.body)
                )
                .subscribe(initialAssesment => {
                    this.diagnosisForm.patchValue({
                        id: initialAssesment.id,
                        smoking: initialAssesment.smoking,
                        incomeDiagnoses: [],
                        comorbidities: [],
                        cardiovascularRisk: initialAssesment.cardiovascularRisk
                    });
                    this.updateFormMeasures(initialAssesment);
                });
        }
    }

    updateFormMeasures(initialAssesment) {
        this.measuresForm.patchValue({
            weight: initialAssesment.weight,
            size: initialAssesment.size,
            iMC: initialAssesment.iMC,
            hbiac: initialAssesment.hbiac,
            baselineFunctionalCapacity: initialAssesment.baselineFunctionalCapacity,
            lDL: initialAssesment.lDL,
            hDL: initialAssesment.hDL
        });

        this.incomeDiagnosisService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIncomeDiagnosis[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIncomeDiagnosis[]>) => response.body)
            )
            .subscribe(
                (res: IIncomeDiagnosis[]) => this.formatDiagnosisPatient(res, initialAssesment),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.comorbiditieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIncomeDiagnosis[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIncomeDiagnosis[]>) => response.body)
            )
            .subscribe(
                (res: IIncomeDiagnosis[]) => this.formatComorbiditiePatient(res, initialAssesment),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    valueChange(array, i, $event) {
        array[i].checked = $event.checked;
    }

    formatDiagnosisPatient(diagnosis, initialAssesment) {
        this.formatInconeDiagnoses(diagnosis);
        this.incomeDiagnosisPatientService
            .findByAssesment({ id: initialAssesment.id })
            .pipe(
                filter((mayBeOk: HttpResponse<IIncomeDiagnosis[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIncomeDiagnosis[]>) => response.body)
            )
            .subscribe(
                (res: IIncomeDiagnosis[]) => this.formatArrayCheckedIncomeDiagnosis(this.incomeDiagnoses, res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    formatComorbiditiePatient(comorbidities, initialAssesment) {
        this.formatComorbidities(comorbidities);
        this.comorbiditiesPatientService
            .findByAssesment({ id: initialAssesment.id })
            .pipe(
                filter((mayBeOk: HttpResponse<IComorbiditie[]>) => mayBeOk.ok),
                map((response: HttpResponse<IComorbiditie[]>) => response.body)
            )
            .subscribe(
                (res: IComorbiditie[]) => this.formatArrayCheckedComorbiditie(this.comorbidities, res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    formatArrayCheckedIncomeDiagnosis(fullArray, filterArray) {
        for (const fA of fullArray) {
            for (const sA of filterArray) {
                if (sA.incomeDiagnosisRelation === fA.id) {
                    if (sA.exist) {
                        fA.checked = true;
                    }
                }
            }
        }
    }

    formatArrayCheckedComorbiditie(fullArray, filterArray) {
        for (const fA of fullArray) {
            for (const sA of filterArray) {
                if (sA.comorbiditieRelation === fA.id) {
                    if (sA.exist) {
                        fA.checked = true;
                    }
                }
            }
        }
    }

    save() {
        this.modal.confirmDialog(this.modalConfirm, () => {
            this.isSaving = true;
            this.global.loading();
            const patient = this.createFromForm();
            if (patient.id !== undefined) {
                patient.rehabStatus = this.patient.rehabStatus;
                patient.sessionNumber = this.patient.sessionNumber;
                patient.deceased = this.patient.deceased;
                patient.abandonment = this.patient.abandonment;
                patient.abandonmentMedicCause = this.patient.abandonmentMedicCause;
                patient.deleted = this.patient.deleted;
                this.subscribeToSaveResponse(this.patientService.update(patient));
            } else {
                patient.rehabStatus = 0;
                patient.sessionNumber = 0;

                this.subscribeToSaveResponse(this.patientService.create(patient));
            }
        });
    }

    private createFromForm(): IPatient {
        return {
            ...new Patient(),
            id: this.initialInfoForm.get(['id']).value,
            code: this.initialInfoForm.get(['code']).value,
            age: this.initialInfoForm.get(['age']).value,
            sex: this.initialInfoForm.get(['sex']).value,
            ocupation: this.initialInfoForm.get(['ocupation']).value,
            scholarship: this.initialInfoForm.get(['scholarship']).value,
            lastEventOcurred: this.initialInfoForm.get(['lastEventOcurred']).value
            // deceased: this.initialInfoForm.get(['deceased']).value,
            // abandonment: this.initialInfoForm.get(['abandonment']).value,
            // abandonmentMedicCause: this.initialInfoForm.get(['abandonmentMedicCause']).value,
            // rehabStatus: this.initialInfoForm.get(['rehabStatus']).value,
            // deleted: this.initialInfoForm.get(['deleted']).value
        };
    }

    private createFromFormInitial(patient): IInitialAssessment {
        return {
            ...new InitialAssessment(),
            id: this.diagnosisForm.get(['id']).value,
            smoking: this.diagnosisForm.get(['smoking']).value,
            weight: this.measuresForm.get(['weight']).value,
            size: this.measuresForm.get(['size']).value,
            iMC: this.measuresForm.get(['iMC']).value,
            hbiac: this.measuresForm.get(['hbiac']).value,
            baselineFunctionalCapacity: this.measuresForm.get(['baselineFunctionalCapacity']).value,
            lDL: this.measuresForm.get(['lDL']).value,
            hDL: this.measuresForm.get(['hDL']).value,
            cardiovascularRisk: this.diagnosisForm.get(['cardiovascularRisk']).value,
            patientId: patient.id
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>) {
        result.subscribe(data => this.onSaveSuccess(data), () => this.onSaveError());
    }

    protected subscribeToSaveResponseInitial(result: Observable<HttpResponse<IInitialAssessment>>) {
        result.subscribe(data => this.onSaveSuccessInitialAssessmentService(data.body), () => this.onSaveError());
    }

    protected onSaveSuccess(result) {
        const initialAssessment = this.createFromFormInitial(result.body);
        if (initialAssessment.id !== null) {
            this.subscribeToSaveResponseInitial(this.initialAssessmentService.update(initialAssessment));
        } else {
            this.subscribeToSaveResponseInitial(this.initialAssessmentService.create(initialAssessment));
        }
    }

    protected onSaveSuccessInitialAssessmentService(initialAssesment) {
        const newIncomeDiagnosis = this.createNewIncomeDiagnosis(initialAssesment);
        const newComorbidities = this.createNewComorbidities(initialAssesment);
        this.totaltoSave = newIncomeDiagnosis.length + newComorbidities.length;
        this.saveNewIncomeDiagnosis(newIncomeDiagnosis);
        this.saveNewComorbidities(newComorbidities);
    }

    saveNewIncomeDiagnosis(newIncomeDiagnosis) {
        for (const newIncomeDiagnose of newIncomeDiagnosis) {
            this.incomeDiagnosisPatientService.create(newIncomeDiagnose).subscribe(response => {
                this.totalSaved++;
                if (this.totaltoSave === this.totalSaved) {
                    this.modal.message(this.modalSuccessMessage);
                    this.isSaving = false;
                    this.previousState();
                    this.global.loaded();
                }
            });
        }
    }

    saveNewComorbidities(newComorbidities) {
        for (const comorbiditie of newComorbidities) {
            this.comorbiditiesPatientService.create(comorbiditie).subscribe(response => {
                this.totalSaved++;
                if (this.totaltoSave === this.totalSaved) {
                    this.modal.message(this.modalSuccessMessage);
                    this.isSaving = false;
                    this.previousState();
                    this.global.loaded();
                }
            });
        }
    }

    createNewComorbidities(initialAssesment) {
        const newComorbidities = [];
        for (const comorbiditie of this.comorbidities) {
            newComorbidities.push({
                ...new ComorbiditiesPatient(),
                id: null,
                description: comorbiditie.description,
                comorbiditieRelation: comorbiditie.id,
                exist: comorbiditie.checked,
                initialAssessmentId: initialAssesment.id
            });
        }
        return newComorbidities;
    }

    createNewIncomeDiagnosis(initialAssesment) {
        const newIncomeDiagnosis = [];
        for (const incomeDiagnosis of this.incomeDiagnoses) {
            newIncomeDiagnosis.push({
                ...new IncomeDiagnosisPatient(),
                id: null,
                description: incomeDiagnosis.description,
                incomeDiagnosisRelation: incomeDiagnosis.id,
                exist: incomeDiagnosis.checked,
                initialAssessmentId: initialAssesment.id
            });
        }
        return newIncomeDiagnosis;
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRehabilitationGroupById(index: number, item: IRehabilitationGroup) {
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
