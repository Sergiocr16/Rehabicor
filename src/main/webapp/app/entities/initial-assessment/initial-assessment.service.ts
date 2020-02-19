import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInitialAssessment } from 'app/shared/model/initial-assessment.model';

type EntityResponseType = HttpResponse<IInitialAssessment>;
type EntityArrayResponseType = HttpResponse<IInitialAssessment[]>;

@Injectable({ providedIn: 'root' })
export class InitialAssessmentService {
    public resourceUrl = SERVER_API_URL + 'api/initial-assessments';

    constructor(protected http: HttpClient) {}

    create(initialAssessment: IInitialAssessment): Observable<EntityResponseType> {
        return this.http.post<IInitialAssessment>(this.resourceUrl, initialAssessment, { observe: 'response' });
    }

    update(initialAssessment: IInitialAssessment): Observable<EntityResponseType> {
        return this.http.put<IInitialAssessment>(this.resourceUrl, initialAssessment, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IInitialAssessment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInitialAssessment[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
