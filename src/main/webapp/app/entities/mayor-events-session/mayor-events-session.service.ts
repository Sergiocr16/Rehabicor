import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMayorEventsSession } from 'app/shared/model/mayor-events-session.model';

type EntityResponseType = HttpResponse<IMayorEventsSession>;
type EntityArrayResponseType = HttpResponse<IMayorEventsSession[]>;

@Injectable({ providedIn: 'root' })
export class MayorEventsSessionService {
    public resourceUrl = SERVER_API_URL + 'api/mayor-events-sessions';

    constructor(protected http: HttpClient) {}

    create(mayorEventsSession: IMayorEventsSession): Observable<EntityResponseType> {
        return this.http.post<IMayorEventsSession>(this.resourceUrl, mayorEventsSession, { observe: 'response' });
    }

    update(mayorEventsSession: IMayorEventsSession): Observable<EntityResponseType> {
        return this.http.put<IMayorEventsSession>(this.resourceUrl, mayorEventsSession, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMayorEventsSession>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMayorEventsSession[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    queryBySession(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMayorEventsSession[]>(this.resourceUrl + '/by-session', { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
