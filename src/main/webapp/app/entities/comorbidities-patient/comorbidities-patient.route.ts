import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ComorbiditiesPatient } from 'app/shared/model/comorbidities-patient.model';
import { ComorbiditiesPatientService } from './comorbidities-patient.service';
import { ComorbiditiesPatientComponent } from './comorbidities-patient.component';
import { ComorbiditiesPatientDetailComponent } from './comorbidities-patient-detail.component';
import { ComorbiditiesPatientUpdateComponent } from './comorbidities-patient-update.component';
import { ComorbiditiesPatientDeletePopupComponent } from './comorbidities-patient-delete-dialog.component';
import { IComorbiditiesPatient } from 'app/shared/model/comorbidities-patient.model';

@Injectable({ providedIn: 'root' })
export class ComorbiditiesPatientResolve implements Resolve<IComorbiditiesPatient> {
    constructor(private service: ComorbiditiesPatientService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IComorbiditiesPatient> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ComorbiditiesPatient>) => response.ok),
                map((comorbiditiesPatient: HttpResponse<ComorbiditiesPatient>) => comorbiditiesPatient.body)
            );
        }
        return of(new ComorbiditiesPatient());
    }
}

export const comorbiditiesPatientRoute: Routes = [
    {
        path: '',
        component: ComorbiditiesPatientComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.comorbiditiesPatient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ComorbiditiesPatientDetailComponent,
        resolve: {
            comorbiditiesPatient: ComorbiditiesPatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.comorbiditiesPatient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ComorbiditiesPatientUpdateComponent,
        resolve: {
            comorbiditiesPatient: ComorbiditiesPatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.comorbiditiesPatient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ComorbiditiesPatientUpdateComponent,
        resolve: {
            comorbiditiesPatient: ComorbiditiesPatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.comorbiditiesPatient.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const comorbiditiesPatientPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ComorbiditiesPatientDeletePopupComponent,
        resolve: {
            comorbiditiesPatient: ComorbiditiesPatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.comorbiditiesPatient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
