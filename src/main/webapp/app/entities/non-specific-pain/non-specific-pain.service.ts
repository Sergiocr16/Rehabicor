import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INonSpecificPain } from 'app/shared/model/non-specific-pain.model';

type EntityResponseType = HttpResponse<INonSpecificPain>;
type EntityArrayResponseType = HttpResponse<INonSpecificPain[]>;

@Injectable({ providedIn: 'root' })
export class NonSpecificPainService {
    public resourceUrl = SERVER_API_URL + 'api/non-specific-pains';

    constructor(protected http: HttpClient) {}

    create(nonSpecificPain: INonSpecificPain): Observable<EntityResponseType> {
        return this.http.post<INonSpecificPain>(this.resourceUrl, nonSpecificPain, { observe: 'response' });
    }

    update(nonSpecificPain: INonSpecificPain): Observable<EntityResponseType> {
        return this.http.put<INonSpecificPain>(this.resourceUrl, nonSpecificPain, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<INonSpecificPain>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INonSpecificPain[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
