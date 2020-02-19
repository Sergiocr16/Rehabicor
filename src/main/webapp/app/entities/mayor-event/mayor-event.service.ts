import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMayorEvent } from 'app/shared/model/mayor-event.model';

type EntityResponseType = HttpResponse<IMayorEvent>;
type EntityArrayResponseType = HttpResponse<IMayorEvent[]>;

@Injectable({ providedIn: 'root' })
export class MayorEventService {
    public resourceUrl = SERVER_API_URL + 'api/mayor-events';

    constructor(protected http: HttpClient) {}

    create(mayorEvent: IMayorEvent): Observable<EntityResponseType> {
        return this.http.post<IMayorEvent>(this.resourceUrl, mayorEvent, { observe: 'response' });
    }

    update(mayorEvent: IMayorEvent): Observable<EntityResponseType> {
        return this.http.put<IMayorEvent>(this.resourceUrl, mayorEvent, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMayorEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMayorEvent[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
