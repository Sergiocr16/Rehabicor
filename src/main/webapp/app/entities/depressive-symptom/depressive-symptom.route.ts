import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DepressiveSymptom } from 'app/shared/model/depressive-symptom.model';
import { DepressiveSymptomService } from './depressive-symptom.service';
import { DepressiveSymptomComponent } from './depressive-symptom.component';
import { DepressiveSymptomDetailComponent } from './depressive-symptom-detail.component';
import { DepressiveSymptomUpdateComponent } from './depressive-symptom-update.component';
import { DepressiveSymptomDeletePopupComponent } from './depressive-symptom-delete-dialog.component';
import { IDepressiveSymptom } from 'app/shared/model/depressive-symptom.model';

@Injectable({ providedIn: 'root' })
export class DepressiveSymptomResolve implements Resolve<IDepressiveSymptom> {
    constructor(private service: DepressiveSymptomService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepressiveSymptom> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DepressiveSymptom>) => response.ok),
                map((depressiveSymptom: HttpResponse<DepressiveSymptom>) => depressiveSymptom.body)
            );
        }
        return of(new DepressiveSymptom());
    }
}

export const depressiveSymptomRoute: Routes = [
    {
        path: '',
        component: DepressiveSymptomComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rehabicorApp.depressiveSymptom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DepressiveSymptomDetailComponent,
        resolve: {
            depressiveSymptom: DepressiveSymptomResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rehabicorApp.depressiveSymptom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DepressiveSymptomUpdateComponent,
        resolve: {
            depressiveSymptom: DepressiveSymptomResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rehabicorApp.depressiveSymptom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DepressiveSymptomUpdateComponent,
        resolve: {
            depressiveSymptom: DepressiveSymptomResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rehabicorApp.depressiveSymptom.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const depressiveSymptomPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DepressiveSymptomDeletePopupComponent,
        resolve: {
            depressiveSymptom: DepressiveSymptomResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rehabicorApp.depressiveSymptom.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
