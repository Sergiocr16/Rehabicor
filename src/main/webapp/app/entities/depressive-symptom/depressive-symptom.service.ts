import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepressiveSymptom } from 'app/shared/model/depressive-symptom.model';

type EntityResponseType = HttpResponse<IDepressiveSymptom>;
type EntityArrayResponseType = HttpResponse<IDepressiveSymptom[]>;

@Injectable({ providedIn: 'root' })
export class DepressiveSymptomService {
    public resourceUrl = SERVER_API_URL + 'api/depressive-symptoms';

    constructor(protected http: HttpClient) {}

    create(depressiveSymptom: IDepressiveSymptom): Observable<EntityResponseType> {
        return this.http.post<IDepressiveSymptom>(this.resourceUrl, depressiveSymptom, { observe: 'response' });
    }

    update(depressiveSymptom: IDepressiveSymptom): Observable<EntityResponseType> {
        return this.http.put<IDepressiveSymptom>(this.resourceUrl, depressiveSymptom, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDepressiveSymptom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDepressiveSymptom[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
