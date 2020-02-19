export interface IMayorEvent {
    id?: number;
    description?: string;
    code?: string;
    deleted?: boolean;
    rehabilitationCenterId?: number;
}

export class MayorEvent implements IMayorEvent {
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
