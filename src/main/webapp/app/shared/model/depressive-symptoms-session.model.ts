export interface IDepressiveSymptomsSession {
    id?: number;
    description?: string;
    exist?: boolean;
    depressiveSymptomRelation?: number;
    sessionId?: number;
}

export class DepressiveSymptomsSession implements IDepressiveSymptomsSession {
    constructor(
        public id?: number,
        public description?: string,
        public exist?: boolean,
        public depressiveSymptomRelation?: number,
        public sessionId?: number
    ) {
        this.exist = this.exist || false;
    }
}
