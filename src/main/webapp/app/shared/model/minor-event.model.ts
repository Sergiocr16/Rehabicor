export interface IMinorEvent {
    id?: number;
    description?: string;
    code?: string;
    deleted?: boolean;
    rehabilitationCenterId?: number;
}

export class MinorEvent implements IMinorEvent {
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
