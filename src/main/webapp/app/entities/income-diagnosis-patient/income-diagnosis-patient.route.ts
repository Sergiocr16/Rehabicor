import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IncomeDiagnosisPatient } from 'app/shared/model/income-diagnosis-patient.model';
import { IncomeDiagnosisPatientService } from './income-diagnosis-patient.service';
import { IncomeDiagnosisPatientComponent } from './income-diagnosis-patient.component';
import { IncomeDiagnosisPatientDetailComponent } from './income-diagnosis-patient-detail.component';
import { IncomeDiagnosisPatientUpdateComponent } from './income-diagnosis-patient-update.component';
import { IncomeDiagnosisPatientDeletePopupComponent } from './income-diagnosis-patient-delete-dialog.component';
import { IIncomeDiagnosisPatient } from 'app/shared/model/income-diagnosis-patient.model';

@Injectable({ providedIn: 'root' })
export class IncomeDiagnosisPatientResolve implements Resolve<IIncomeDiagnosisPatient> {
    constructor(private service: IncomeDiagnosisPatientService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIncomeDiagnosisPatient> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<IncomeDiagnosisPatient>) => response.ok),
                map((incomeDiagnosisPatient: HttpResponse<IncomeDiagnosisPatient>) => incomeDiagnosisPatient.body)
            );
        }
        return of(new IncomeDiagnosisPatient());
    }
}

export const incomeDiagnosisPatientRoute: Routes = [
    {
        path: '',
        component: IncomeDiagnosisPatientComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.incomeDiagnosisPatient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: IncomeDiagnosisPatientDetailComponent,
        resolve: {
            incomeDiagnosisPatient: IncomeDiagnosisPatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.incomeDiagnosisPatient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: IncomeDiagnosisPatientUpdateComponent,
        resolve: {
            incomeDiagnosisPatient: IncomeDiagnosisPatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.incomeDiagnosisPatient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: IncomeDiagnosisPatientUpdateComponent,
        resolve: {
            incomeDiagnosisPatient: IncomeDiagnosisPatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.incomeDiagnosisPatient.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const incomeDiagnosisPatientPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: IncomeDiagnosisPatientDeletePopupComponent,
        resolve: {
            incomeDiagnosisPatient: IncomeDiagnosisPatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.incomeDiagnosisPatient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
