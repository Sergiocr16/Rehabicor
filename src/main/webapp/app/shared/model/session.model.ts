import { Moment } from 'moment';
import { IDepressiveSymptomsSession } from 'app/shared/model/depressive-symptoms-session.model';
import { IMayorEventsSession } from 'app/shared/model/mayor-events-session.model';
import { IMinorEventsSession } from 'app/shared/model/minor-events-session.model';
import { INonSpecificPainsSession } from 'app/shared/model/non-specific-pains-session.model';

export interface ISession {
    id?: number;
    code?: string;
    executionDate?: Moment;
    abscence?: boolean;
    hospitalization?: boolean;
    status?: number;
    deleted?: boolean;
    currentlyWorking?: boolean;
    depressiveSymptomsSessions?: IDepressiveSymptomsSession[];
    mayorEventsSessions?: IMayorEventsSession[];
    minorEventsSessions?: IMinorEventsSession[];
    nonSpecificPainsSessions?: INonSpecificPainsSession[];
    patientId?: number;
}

export class Session implements ISession {
    constructor(
        public id?: number,
        public code?: string,
        public executionDate?: Moment,
        public abscence?: boolean,
        public hospitalization?: boolean,
        public status?: number,
        public deleted?: boolean,
        public currentlyWorking?: boolean,
        public depressiveSymptomsSessions?: IDepressiveSymptomsSession[],
        public mayorEventsSessions?: IMayorEventsSession[],
        public minorEventsSessions?: IMinorEventsSession[],
        public nonSpecificPainsSessions?: INonSpecificPainsSession[],
        public patientId?: number
    ) {
        this.abscence = this.abscence || false;
        this.hospitalization = this.hospitalization || false;
        this.deleted = this.deleted || false;
        this.currentlyWorking = this.currentlyWorking || false;
    }
}
