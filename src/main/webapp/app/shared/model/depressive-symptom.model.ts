export interface IDepressiveSymptom {
    id?: number;
    description?: string;
    code?: string;
    deleted?: boolean;
    rehabilitationCenterId?: number;
}

export class DepressiveSymptom implements IDepressiveSymptom {
    constructor(
        public id?: number,
        public description?: string,
        public code?: string,
        public deleted?: boolean,
        public rehabilitationCenterId?: number
    ) {
        this.deleted = this.deleted || false;
    }
}
