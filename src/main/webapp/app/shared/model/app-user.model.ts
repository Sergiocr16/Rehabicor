export interface IAppUser {
    id?: number;
    name?: string;
    lastName?: string;
    authorityType?: number;
    status?: number;
    userLogin?: string;
    userId?: number;
    rehabilitationCenterId?: number;
}

export class AppUser implements IAppUser {
    constructor(
        public id?: number,
        public name?: string,
        public lastName?: string,
        public authorityType?: number,
        public status?: number,
        public userLogin?: string,
        public userId?: number,
        public rehabilitationCenterId?: number
    ) {}
}
