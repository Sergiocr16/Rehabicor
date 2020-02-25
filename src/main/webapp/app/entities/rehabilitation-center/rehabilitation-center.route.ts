import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from './rehabilitation-center.service';
import { RehabilitationCenterComponent } from './rehabilitation-center.component';
import { RehabilitationCenterDetailComponent } from './rehabilitation-center-detail.component';
import { RehabilitationCenterUpdateComponent } from './rehabilitation-center-update.component';
import { RehabilitationCenterDeletePopupComponent } from './rehabilitation-center-delete-dialog.component';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';

@Injectable({ providedIn: 'root' })
export class RehabilitationCenterResolve implements Resolve<IRehabilitationCenter> {
    constructor(private service: RehabilitationCenterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRehabilitationCenter> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RehabilitationCenter>) => response.ok),
                map((rehabilitationCenter: HttpResponse<RehabilitationCenter>) => rehabilitationCenter.body)
            );
        }
        return of(new RehabilitationCenter());
    }
}

export const rehabilitationCenterRoute: Routes = [
    {
        path: '',
        component: RehabilitationCenterComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rehabicorApp.rehabilitationCenter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RehabilitationCenterDetailComponent,
        resolve: {
            rehabilitationCenter: RehabilitationCenterResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rehabicorApp.rehabilitationCenter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RehabilitationCenterUpdateComponent,
        resolve: {
            rehabilitationCenter: RehabilitationCenterResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rehabicorApp.rehabilitationCenter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RehabilitationCenterUpdateComponent,
        resolve: {
            rehabilitationCenter: RehabilitationCenterResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rehabicorApp.rehabilitationCenter.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rehabilitationCenterPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RehabilitationCenterDeletePopupComponent,
        resolve: {
            rehabilitationCenter: RehabilitationCenterResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rehabicorApp.rehabilitationCenter.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
