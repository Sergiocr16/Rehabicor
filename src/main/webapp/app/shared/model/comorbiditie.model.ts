export interface IComorbiditie {
    id?: number;
    description?: string;
    deleted?: boolean;
    rehabilitationCenterId?: number;
}

export class Comorbiditie implements IComorbiditie {
    constructor(public id?: number, public description?: string, public deleted?: boolean, public rehabilitationCenterId?: number) {
        this.deleted = this.deleted || false;
    }
}
