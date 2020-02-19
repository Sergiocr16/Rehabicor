import { Moment } from 'moment';
import { IPatient } from 'app/shared/model/patient.model';

export interface IRehabilitationGroup {
    id?: number;
    name?: string;
    creationDate?: Moment;
    programStatus?: number;
    deleted?: boolean;
    patients?: IPatient[];
    rehabilitationCenterId?: number;
}

export class RehabilitationGroup implements IRehabilitationGroup {
    constructor(
        public id?: number,
        public name?: string,
        public creationDate?: Moment,
        public programStatus?: number,
        public deleted?: boolean,
        public patients?: IPatient[],
        public rehabilitationCenterId?: number
    ) {
        this.deleted = this.deleted || false;
    }
}
