export interface IComorbiditiesPatient {
    id?: number;
    description?: string;
    exist?: boolean;
    comorbiditieRelation?: number;
    initialAssessmentId?: number;
}

export class ComorbiditiesPatient implements IComorbiditiesPatient {
    constructor(
        public id?: number,
        public description?: string,
        public exist?: boolean,
        public comorbiditieRelation?: number,
        public initialAssessmentId?: number
    ) {
        this.exist = this.exist || false;
    }
}
