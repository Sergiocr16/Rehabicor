import { Moment } from 'moment';

export interface IFinalAssessment {
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
    isWorking?: boolean;
    deceased?: boolean;
    abandonment?: boolean;
    abandonmentMedicCause?: boolean;
    hospitalized?: boolean;
    deleted?: boolean;
    reevaluation?: boolean;
    executionDate?: Moment;
    patientId?: number;
}

export class FinalAssessment implements IFinalAssessment {
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
        public isWorking?: boolean,
        public deceased?: boolean,
        public abandonment?: boolean,
        public abandonmentMedicCause?: boolean,
        public hospitalized?: boolean,
        public deleted?: boolean,
        public reevaluation?: boolean,
        public executionDate?: Moment,
        public patientId?: number
    ) {
        this.isWorking = this.isWorking || false;
        this.deceased = this.deceased || false;
        this.abandonment = this.abandonment || false;
        this.abandonmentMedicCause = this.abandonmentMedicCause || false;
        this.hospitalized = this.hospitalized || false;
        this.deleted = this.deleted || false;
        this.reevaluation = this.reevaluation || false;
    }
}
