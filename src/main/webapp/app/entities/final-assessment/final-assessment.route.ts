import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FinalAssessment } from 'app/shared/model/final-assessment.model';
import { FinalAssessmentService } from './final-assessment.service';
import { FinalAssessmentComponent } from './final-assessment.component';
import { FinalAssessmentDetailComponent } from './final-assessment-detail.component';
import { FinalAssessmentUpdateComponent } from './final-assessment-update.component';
import { FinalAssessmentDeletePopupComponent } from './final-assessment-delete-dialog.component';
import { IFinalAssessment } from 'app/shared/model/final-assessment.model';

@Injectable({ providedIn: 'root' })
export class FinalAssessmentResolve implements Resolve<IFinalAssessment> {
    constructor(private service: FinalAssessmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFinalAssessment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FinalAssessment>) => response.ok),
                map((finalAssessment: HttpResponse<FinalAssessment>) => finalAssessment.body)
            );
        }
        return of(new FinalAssessment());
    }
}

export const finalAssessmentRoute: Routes = [
    {
        path: '',
        component: FinalAssessmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.finalAssessment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FinalAssessmentDetailComponent,
        resolve: {
            finalAssessment: FinalAssessmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.finalAssessment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FinalAssessmentUpdateComponent,
        resolve: {
            finalAssessment: FinalAssessmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.finalAssessment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FinalAssessmentUpdateComponent,
        resolve: {
            finalAssessment: FinalAssessmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.finalAssessment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const finalAssessmentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FinalAssessmentDeletePopupComponent,
        resolve: {
            finalAssessment: FinalAssessmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.finalAssessment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
