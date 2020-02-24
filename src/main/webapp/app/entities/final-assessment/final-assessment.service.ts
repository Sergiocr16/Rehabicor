import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFinalAssessment } from 'app/shared/model/final-assessment.model';

type EntityResponseType = HttpResponse<IFinalAssessment>;
type EntityArrayResponseType = HttpResponse<IFinalAssessment[]>;

@Injectable({ providedIn: 'root' })
export class FinalAssessmentService {
    public resourceUrl = SERVER_API_URL + 'api/final-assessments';

    constructor(protected http: HttpClient) {}

    create(finalAssessment: IFinalAssessment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(finalAssessment);
        return this.http
            .post<IFinalAssessment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(finalAssessment: IFinalAssessment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(finalAssessment);
        return this.http
            .put<IFinalAssessment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFinalAssessment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFinalAssessment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    queryByPatient(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFinalAssessment[]>(this.resourceUrl + '/by-patient', { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(finalAssessment: IFinalAssessment): IFinalAssessment {
        const copy: IFinalAssessment = Object.assign({}, finalAssessment, {
            executionDate:
                finalAssessment.executionDate != null && finalAssessment.executionDate.isValid()
                    ? finalAssessment.executionDate.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.executionDate = res.body.executionDate != null ? moment(res.body.executionDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((finalAssessment: IFinalAssessment) => {
                finalAssessment.executionDate = finalAssessment.executionDate != null ? moment(finalAssessment.executionDate) : null;
            });
        }
        return res;
    }
}
