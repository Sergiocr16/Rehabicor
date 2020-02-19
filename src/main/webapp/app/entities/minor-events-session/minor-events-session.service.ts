import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMinorEventsSession } from 'app/shared/model/minor-events-session.model';

type EntityResponseType = HttpResponse<IMinorEventsSession>;
type EntityArrayResponseType = HttpResponse<IMinorEventsSession[]>;

@Injectable({ providedIn: 'root' })
export class MinorEventsSessionService {
    public resourceUrl = SERVER_API_URL + 'api/minor-events-sessions';

    constructor(protected http: HttpClient) {}

    create(minorEventsSession: IMinorEventsSession): Observable<EntityResponseType> {
        return this.http.post<IMinorEventsSession>(this.resourceUrl, minorEventsSession, { observe: 'response' });
    }

    update(minorEventsSession: IMinorEventsSession): Observable<EntityResponseType> {
        return this.http.put<IMinorEventsSession>(this.resourceUrl, minorEventsSession, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMinorEventsSession>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMinorEventsSession[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
