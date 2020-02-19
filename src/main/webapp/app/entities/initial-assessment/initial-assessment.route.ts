import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InitialAssessment } from 'app/shared/model/initial-assessment.model';
import { InitialAssessmentService } from './initial-assessment.service';
import { InitialAssessmentComponent } from './initial-assessment.component';
import { InitialAssessmentDetailComponent } from './initial-assessment-detail.component';
import { InitialAssessmentUpdateComponent } from './initial-assessment-update.component';
import { InitialAssessmentDeletePopupComponent } from './initial-assessment-delete-dialog.component';
import { IInitialAssessment } from 'app/shared/model/initial-assessment.model';

@Injectable({ providedIn: 'root' })
export class InitialAssessmentResolve implements Resolve<IInitialAssessment> {
    constructor(private service: InitialAssessmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInitialAssessment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<InitialAssessment>) => response.ok),
                map((initialAssessment: HttpResponse<InitialAssessment>) => initialAssessment.body)
            );
        }
        return of(new InitialAssessment());
    }
}

export const initialAssessmentRoute: Routes = [
    {
        path: '',
        component: InitialAssessmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.initialAssessment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InitialAssessmentDetailComponent,
        resolve: {
            initialAssessment: InitialAssessmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.initialAssessment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: InitialAssessmentUpdateComponent,
        resolve: {
            initialAssessment: InitialAssessmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.initialAssessment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: InitialAssessmentUpdateComponent,
        resolve: {
            initialAssessment: InitialAssessmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.initialAssessment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const initialAssessmentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: InitialAssessmentDeletePopupComponent,
        resolve: {
            initialAssessment: InitialAssessmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.initialAssessment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
