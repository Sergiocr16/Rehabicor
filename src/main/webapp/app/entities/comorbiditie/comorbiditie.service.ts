import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IComorbiditie } from 'app/shared/model/comorbiditie.model';

type EntityResponseType = HttpResponse<IComorbiditie>;
type EntityArrayResponseType = HttpResponse<IComorbiditie[]>;

@Injectable({ providedIn: 'root' })
export class ComorbiditieService {
    public resourceUrl = SERVER_API_URL + 'api/comorbidities';

    constructor(protected http: HttpClient) {}

    create(comorbiditie: IComorbiditie): Observable<EntityResponseType> {
        return this.http.post<IComorbiditie>(this.resourceUrl, comorbiditie, { observe: 'response' });
    }

    update(comorbiditie: IComorbiditie): Observable<EntityResponseType> {
        return this.http.put<IComorbiditie>(this.resourceUrl, comorbiditie, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IComorbiditie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IComorbiditie[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
