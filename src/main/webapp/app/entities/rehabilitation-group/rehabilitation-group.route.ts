import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';
import { RehabilitationGroupService } from './rehabilitation-group.service';
import { RehabilitationGroupComponent } from './rehabilitation-group.component';
import { RehabilitationGroupDetailComponent } from './rehabilitation-group-detail.component';
import { RehabilitationGroupUpdateComponent } from './rehabilitation-group-update.component';
import { RehabilitationGroupDeletePopupComponent } from './rehabilitation-group-delete-dialog.component';
import { IRehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';

@Injectable({ providedIn: 'root' })
export class RehabilitationGroupResolve implements Resolve<IRehabilitationGroup> {
    constructor(private service: RehabilitationGroupService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRehabilitationGroup> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RehabilitationGroup>) => response.ok),
                map((rehabilitationGroup: HttpResponse<RehabilitationGroup>) => rehabilitationGroup.body)
            );
        }
        return of(new RehabilitationGroup());
    }
}

export const rehabilitationGroupRoute: Routes = [
    {
        path: '',
        component: RehabilitationGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.rehabilitationGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RehabilitationGroupDetailComponent,
        resolve: {
            rehabilitationGroup: RehabilitationGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.rehabilitationGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RehabilitationGroupUpdateComponent,
        resolve: {
            rehabilitationGroup: RehabilitationGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.rehabilitationGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RehabilitationGroupUpdateComponent,
        resolve: {
            rehabilitationGroup: RehabilitationGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.rehabilitationGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rehabilitationGroupPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RehabilitationGroupDeletePopupComponent,
        resolve: {
            rehabilitationGroup: RehabilitationGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.rehabilitationGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
