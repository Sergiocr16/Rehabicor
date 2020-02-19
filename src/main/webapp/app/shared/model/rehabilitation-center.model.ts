import { IAppUser } from 'app/shared/model/app-user.model';
import { IComorbiditie } from 'app/shared/model/comorbiditie.model';
import { IDepressiveSymptom } from 'app/shared/model/depressive-symptom.model';
import { IIncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';
import { IMayorEvent } from 'app/shared/model/mayor-event.model';
import { IMinorEvent } from 'app/shared/model/minor-event.model';
import { INonSpecificPain } from 'app/shared/model/non-specific-pain.model';
import { IRehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';

export interface IRehabilitationCenter {
    id?: number;
    name?: string;
    telephone?: string;
    deleted?: boolean;
    status?: number;
    appUsers?: IAppUser[];
    comorbidities?: IComorbiditie[];
    depressiveSymptoms?: IDepressiveSymptom[];
    incomeDiagnoses?: IIncomeDiagnosis[];
    mayorEvents?: IMayorEvent[];
    minorEvents?: IMinorEvent[];
    nonSpecificPains?: INonSpecificPain[];
    rehabilitationGroups?: IRehabilitationGroup[];
}

export class RehabilitationCenter implements IRehabilitationCenter {
    constructor(
        public id?: number,
        public name?: string,
        public telephone?: string,
        public deleted?: boolean,
        public status?: number,
        public appUsers?: IAppUser[],
        public comorbidities?: IComorbiditie[],
        public depressiveSymptoms?: IDepressiveSymptom[],
        public incomeDiagnoses?: IIncomeDiagnosis[],
        public mayorEvents?: IMayorEvent[],
        public minorEvents?: IMinorEvent[],
        public nonSpecificPains?: INonSpecificPain[],
        public rehabilitationGroups?: IRehabilitationGroup[]
    ) {
        this.deleted = this.deleted || false;
    }
}
