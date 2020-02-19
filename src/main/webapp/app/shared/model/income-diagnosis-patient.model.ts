export interface IIncomeDiagnosisPatient {
    id?: number;
    description?: string;
    exist?: boolean;
    incomeDiagnosisRelation?: number;
    initialAssessmentId?: number;
}

export class IncomeDiagnosisPatient implements IIncomeDiagnosisPatient {
    constructor(
        public id?: number,
        public description?: string,
        public exist?: boolean,
        public incomeDiagnosisRelation?: number,
        public initialAssessmentId?: number
    ) {
        this.exist = this.exist || false;
    }
}
