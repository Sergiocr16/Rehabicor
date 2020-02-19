export interface INonSpecificPain {
    id?: number;
    description?: string;
    code?: string;
    deleted?: boolean;
    rehabilitationCenterId?: number;
}

export class NonSpecificPain implements INonSpecificPain {
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
