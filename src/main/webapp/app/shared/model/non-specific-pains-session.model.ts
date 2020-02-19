export interface INonSpecificPainsSession {
    id?: number;
    description?: string;
    exist?: boolean;
    nonSpecificPainsSessionRelation?: number;
    sessionId?: number;
}

export class NonSpecificPainsSession implements INonSpecificPainsSession {
    constructor(
        public id?: number,
        public description?: string,
        public exist?: boolean,
        public nonSpecificPainsSessionRelation?: number,
        public sessionId?: number
    ) {
        this.exist = this.exist || false;
    }
}
