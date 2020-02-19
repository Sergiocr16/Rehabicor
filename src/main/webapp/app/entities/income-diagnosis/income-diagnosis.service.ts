import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';

type EntityResponseType = HttpResponse<IIncomeDiagnosis>;
type EntityArrayResponseType = HttpResponse<IIncomeDiagnosis[]>;

@Injectable({ providedIn: 'root' })
export class IncomeDiagnosisService {
    public resourceUrl = SERVER_API_URL + 'api/income-diagnoses';

    constructor(protected http: HttpClient) {}

    create(incomeDiagnosis: IIncomeDiagnosis): Observable<EntityResponseType> {
        return this.http.post<IIncomeDiagnosis>(this.resourceUrl, incomeDiagnosis, { observe: 'response' });
    }

    update(incomeDiagnosis: IIncomeDiagnosis): Observable<EntityResponseType> {
        return this.http.put<IIncomeDiagnosis>(this.resourceUrl, incomeDiagnosis, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIncomeDiagnosis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIncomeDiagnosis[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
