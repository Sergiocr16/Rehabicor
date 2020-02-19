export interface IIncomeDiagnosis {
    id?: number;
    description?: string;
    deleted?: boolean;
    rehabilitationCenterId?: number;
}

export class IncomeDiagnosis implements IIncomeDiagnosis {
    constructor(public id?: number, public description?: string, public deleted?: boolean, public rehabilitationCenterId?: number) {
        this.deleted = this.deleted || false;
    }
}
