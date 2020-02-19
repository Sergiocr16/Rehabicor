import { IFinalAssessment } from 'app/shared/model/final-assessment.model';
import { IInitialAssessment } from 'app/shared/model/initial-assessment.model';
import { ISession } from 'app/shared/model/session.model';
import { IRehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';

export interface IPatient {
    id?: number;
    code?: string;
    age?: number;
    sex?: string;
    ocupation?: string;
    lastEventOcurred?: number;
    deceased?: boolean;
    abandonment?: boolean;
    abandonmentMedicCause?: boolean;
    rehabStatus?: number;
    sessionNumber?: number;
    deleted?: boolean;
    scholarship?: string;
    finalAssessments?: IFinalAssessment[];
    initialAssessments?: IInitialAssessment[];
    sessions?: ISession[];
    rehabilitationGroups?: IRehabilitationGroup[];
}

export class Patient implements IPatient {
    constructor(
        public id?: number,
        public code?: string,
        public age?: number,
        public sex?: string,
        public ocupation?: string,
        public lastEventOcurred?: number,
        public deceased?: boolean,
        public abandonment?: boolean,
        public abandonmentMedicCause?: boolean,
        public rehabStatus?: number,
        public sessionNumber?: number,
        public deleted?: boolean,
        public scholarship?: string,
        public finalAssessments?: IFinalAssessment[],
        public initialAssessments?: IInitialAssessment[],
        public sessions?: ISession[],
        public rehabilitationGroups?: IRehabilitationGroup[]
    ) {
        this.deceased = this.deceased || false;
        this.abandonment = this.abandonment || false;
        this.abandonmentMedicCause = this.abandonmentMedicCause || false;
        this.deleted = this.deleted || false;
    }
}
