import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IComorbiditiesPatient } from 'app/shared/model/comorbidities-patient.model';

type EntityResponseType = HttpResponse<IComorbiditiesPatient>;
type EntityArrayResponseType = HttpResponse<IComorbiditiesPatient[]>;

@Injectable({ providedIn: 'root' })
export class ComorbiditiesPatientService {
    public resourceUrl = SERVER_API_URL + 'api/comorbidities-patients';

    constructor(protected http: HttpClient) {}

    create(comorbiditiesPatient: IComorbiditiesPatient): Observable<EntityResponseType> {
        return this.http.post<IComorbiditiesPatient>(this.resourceUrl, comorbiditiesPatient, { observe: 'response' });
    }

    update(comorbiditiesPatient: IComorbiditiesPatient): Observable<EntityResponseType> {
        return this.http.put<IComorbiditiesPatient>(this.resourceUrl, comorbiditiesPatient, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IComorbiditiesPatient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IComorbiditiesPatient[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
