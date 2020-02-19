export interface IMayorEventsSession {
    id?: number;
    description?: string;
    exist?: boolean;
    mayorEventRelation?: number;
    sessionId?: number;
}

export class MayorEventsSession implements IMayorEventsSession {
    constructor(
        public id?: number,
        public description?: string,
        public exist?: boolean,
        public mayorEventRelation?: number,
        public sessionId?: number
    ) {
        this.exist = this.exist || false;
    }
}
