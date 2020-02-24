import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIncomeDiagnosisPatient } from 'app/shared/model/income-diagnosis-patient.model';

type EntityResponseType = HttpResponse<IIncomeDiagnosisPatient>;
type EntityArrayResponseType = HttpResponse<IIncomeDiagnosisPatient[]>;

@Injectable({ providedIn: 'root' })
export class IncomeDiagnosisPatientService {
    public resourceUrl = SERVER_API_URL + 'api/income-diagnosis-patients';

    constructor(protected http: HttpClient) {}

    create(incomeDiagnosisPatient: IIncomeDiagnosisPatient): Observable<EntityResponseType> {
        return this.http.post<IIncomeDiagnosisPatient>(this.resourceUrl, incomeDiagnosisPatient, { observe: 'response' });
    }

    update(incomeDiagnosisPatient: IIncomeDiagnosisPatient): Observable<EntityResponseType> {
        return this.http.put<IIncomeDiagnosisPatient>(this.resourceUrl, incomeDiagnosisPatient, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIncomeDiagnosisPatient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
    findByAssesment(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIncomeDiagnosisPatient[]>(`${this.resourceUrl}/by-asessment/`, { params: options, observe: 'response' });
    }
    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIncomeDiagnosisPatient[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
