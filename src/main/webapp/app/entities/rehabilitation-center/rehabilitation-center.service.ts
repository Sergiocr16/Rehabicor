import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';

type EntityResponseType = HttpResponse<IRehabilitationCenter>;
type EntityArrayResponseType = HttpResponse<IRehabilitationCenter[]>;

@Injectable({ providedIn: 'root' })
export class RehabilitationCenterService {
    public resourceUrl = SERVER_API_URL + 'api/rehabilitation-centers';

    constructor(protected http: HttpClient) {}

    create(rehabilitationCenter: IRehabilitationCenter): Observable<EntityResponseType> {
        return this.http.post<IRehabilitationCenter>(this.resourceUrl, rehabilitationCenter, { observe: 'response' });
    }

    update(rehabilitationCenter: IRehabilitationCenter): Observable<EntityResponseType> {
        return this.http.put<IRehabilitationCenter>(this.resourceUrl, rehabilitationCenter, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRehabilitationCenter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRehabilitationCenter[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
