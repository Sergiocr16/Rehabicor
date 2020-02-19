import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NonSpecificPain } from 'app/shared/model/non-specific-pain.model';
import { NonSpecificPainService } from './non-specific-pain.service';
import { NonSpecificPainComponent } from './non-specific-pain.component';
import { NonSpecificPainDetailComponent } from './non-specific-pain-detail.component';
import { NonSpecificPainUpdateComponent } from './non-specific-pain-update.component';
import { NonSpecificPainDeletePopupComponent } from './non-specific-pain-delete-dialog.component';
import { INonSpecificPain } from 'app/shared/model/non-specific-pain.model';

@Injectable({ providedIn: 'root' })
export class NonSpecificPainResolve implements Resolve<INonSpecificPain> {
    constructor(private service: NonSpecificPainService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INonSpecificPain> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<NonSpecificPain>) => response.ok),
                map((nonSpecificPain: HttpResponse<NonSpecificPain>) => nonSpecificPain.body)
            );
        }
        return of(new NonSpecificPain());
    }
}

export const nonSpecificPainRoute: Routes = [
    {
        path: '',
        component: NonSpecificPainComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.nonSpecificPain.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: NonSpecificPainDetailComponent,
        resolve: {
            nonSpecificPain: NonSpecificPainResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.nonSpecificPain.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: NonSpecificPainUpdateComponent,
        resolve: {
            nonSpecificPain: NonSpecificPainResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.nonSpecificPain.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: NonSpecificPainUpdateComponent,
        resolve: {
            nonSpecificPain: NonSpecificPainResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.nonSpecificPain.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nonSpecificPainPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: NonSpecificPainDeletePopupComponent,
        resolve: {
            nonSpecificPain: NonSpecificPainResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.nonSpecificPain.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
