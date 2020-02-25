import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INonSpecificPainsSession } from 'app/shared/model/non-specific-pains-session.model';

type EntityResponseType = HttpResponse<INonSpecificPainsSession>;
type EntityArrayResponseType = HttpResponse<INonSpecificPainsSession[]>;

@Injectable({ providedIn: 'root' })
export class NonSpecificPainsSessionService {
    public resourceUrl = SERVER_API_URL + 'api/non-specific-pains-sessions';

    constructor(protected http: HttpClient) {}

    create(nonSpecificPainsSession: INonSpecificPainsSession): Observable<EntityResponseType> {
        return this.http.post<INonSpecificPainsSession>(this.resourceUrl, nonSpecificPainsSession, { observe: 'response' });
    }

    update(nonSpecificPainsSession: INonSpecificPainsSession): Observable<EntityResponseType> {
        return this.http.put<INonSpecificPainsSession>(this.resourceUrl, nonSpecificPainsSession, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<INonSpecificPainsSession>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INonSpecificPainsSession[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    queryBySession(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INonSpecificPainsSession[]>(this.resourceUrl + '/by-session', { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
