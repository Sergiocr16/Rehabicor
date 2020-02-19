import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';
import { IncomeDiagnosisService } from './income-diagnosis.service';
import { IncomeDiagnosisComponent } from './income-diagnosis.component';
import { IncomeDiagnosisDetailComponent } from './income-diagnosis-detail.component';
import { IncomeDiagnosisUpdateComponent } from './income-diagnosis-update.component';
import { IncomeDiagnosisDeletePopupComponent } from './income-diagnosis-delete-dialog.component';
import { IIncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';

@Injectable({ providedIn: 'root' })
export class IncomeDiagnosisResolve implements Resolve<IIncomeDiagnosis> {
    constructor(private service: IncomeDiagnosisService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIncomeDiagnosis> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<IncomeDiagnosis>) => response.ok),
                map((incomeDiagnosis: HttpResponse<IncomeDiagnosis>) => incomeDiagnosis.body)
            );
        }
        return of(new IncomeDiagnosis());
    }
}

export const incomeDiagnosisRoute: Routes = [
    {
        path: '',
        component: IncomeDiagnosisComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.incomeDiagnosis.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: IncomeDiagnosisDetailComponent,
        resolve: {
            incomeDiagnosis: IncomeDiagnosisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.incomeDiagnosis.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: IncomeDiagnosisUpdateComponent,
        resolve: {
            incomeDiagnosis: IncomeDiagnosisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.incomeDiagnosis.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: IncomeDiagnosisUpdateComponent,
        resolve: {
            incomeDiagnosis: IncomeDiagnosisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.incomeDiagnosis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const incomeDiagnosisPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: IncomeDiagnosisDeletePopupComponent,
        resolve: {
            incomeDiagnosis: IncomeDiagnosisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.incomeDiagnosis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
