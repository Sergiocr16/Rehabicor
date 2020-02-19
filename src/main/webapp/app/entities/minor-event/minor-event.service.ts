import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMinorEvent } from 'app/shared/model/minor-event.model';

type EntityResponseType = HttpResponse<IMinorEvent>;
type EntityArrayResponseType = HttpResponse<IMinorEvent[]>;

@Injectable({ providedIn: 'root' })
export class MinorEventService {
    public resourceUrl = SERVER_API_URL + 'api/minor-events';

    constructor(protected http: HttpClient) {}

    create(minorEvent: IMinorEvent): Observable<EntityResponseType> {
        return this.http.post<IMinorEvent>(this.resourceUrl, minorEvent, { observe: 'response' });
    }

    update(minorEvent: IMinorEvent): Observable<EntityResponseType> {
        return this.http.put<IMinorEvent>(this.resourceUrl, minorEvent, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMinorEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMinorEvent[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
