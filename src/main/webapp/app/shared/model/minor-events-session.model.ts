export interface IMinorEventsSession {
    id?: number;
    description?: string;
    exist?: boolean;
    minorEventRelation?: number;
    sessionId?: number;
}

export class MinorEventsSession implements IMinorEventsSession {
    constructor(
        public id?: number,
        public description?: string,
        public exist?: boolean,
        public minorEventRelation?: number,
        public sessionId?: number
    ) {
        this.exist = this.exist || false;
    }
}
