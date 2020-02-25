import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepressiveSymptomsSession } from 'app/shared/model/depressive-symptoms-session.model';

type EntityResponseType = HttpResponse<IDepressiveSymptomsSession>;
type EntityArrayResponseType = HttpResponse<IDepressiveSymptomsSession[]>;

@Injectable({ providedIn: 'root' })
export class DepressiveSymptomsSessionService {
    public resourceUrl = SERVER_API_URL + 'api/depressive-symptoms-sessions';

    constructor(protected http: HttpClient) {}

    create(depressiveSymptomsSession: IDepressiveSymptomsSession): Observable<EntityResponseType> {
        return this.http.post<IDepressiveSymptomsSession>(this.resourceUrl, depressiveSymptomsSession, { observe: 'response' });
    }

    update(depressiveSymptomsSession: IDepressiveSymptomsSession): Observable<EntityResponseType> {
        return this.http.put<IDepressiveSymptomsSession>(this.resourceUrl, depressiveSymptomsSession, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDepressiveSymptomsSession>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
    queryBySession(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDepressiveSymptomsSession[]>(this.resourceUrl + '/by-session', { params: options, observe: 'response' });
    }
    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDepressiveSymptomsSession[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
