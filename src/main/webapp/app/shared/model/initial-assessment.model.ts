import { IComorbiditiesPatient } from 'app/shared/model/comorbidities-patient.model';
import { IIncomeDiagnosisPatient } from 'app/shared/model/income-diagnosis-patient.model';

export interface IInitialAssessment {
    id?: number;
    smoking?: string;
    weight?: string;
    size?: string;
    iMC?: string;
    hbiac?: string;
    baselineFunctionalCapacity?: string;
    lDL?: string;
    hDL?: string;
    cardiovascularRisk?: string;
    deleted?: boolean;
    comorbiditiesPatients?: IComorbiditiesPatient[];
    incomeDiagnosisPatients?: IIncomeDiagnosisPatient[];
    patientId?: number;
}

export class InitialAssessment implements IInitialAssessment {
    constructor(
        public id?: number,
        public smoking?: string,
        public weight?: string,
        public size?: string,
        public iMC?: string,
        public hbiac?: string,
        public baselineFunctionalCapacity?: string,
        public lDL?: string,
        public hDL?: string,
        public cardiovascularRisk?: string,
        public deleted?: boolean,
        public comorbiditiesPatients?: IComorbiditiesPatient[],
        public incomeDiagnosisPatients?: IIncomeDiagnosisPatient[],
        public patientId?: number
    ) {
        this.deleted = this.deleted || false;
    }
}
