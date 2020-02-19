import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DepressiveSymptomsSession } from 'app/shared/model/depressive-symptoms-session.model';
import { DepressiveSymptomsSessionService } from './depressive-symptoms-session.service';
import { DepressiveSymptomsSessionComponent } from './depressive-symptoms-session.component';
import { DepressiveSymptomsSessionDetailComponent } from './depressive-symptoms-session-detail.component';
import { DepressiveSymptomsSessionUpdateComponent } from './depressive-symptoms-session-update.component';
import { DepressiveSymptomsSessionDeletePopupComponent } from './depressive-symptoms-session-delete-dialog.component';
import { IDepressiveSymptomsSession } from 'app/shared/model/depressive-symptoms-session.model';

@Injectable({ providedIn: 'root' })
export class DepressiveSymptomsSessionResolve implements Resolve<IDepressiveSymptomsSession> {
    constructor(private service: DepressiveSymptomsSessionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepressiveSymptomsSession> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DepressiveSymptomsSession>) => response.ok),
                map((depressiveSymptomsSession: HttpResponse<DepressiveSymptomsSession>) => depressiveSymptomsSession.body)
            );
        }
        return of(new DepressiveSymptomsSession());
    }
}

export const depressiveSymptomsSessionRoute: Routes = [
    {
        path: '',
        component: DepressiveSymptomsSessionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.depressiveSymptomsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DepressiveSymptomsSessionDetailComponent,
        resolve: {
            depressiveSymptomsSession: DepressiveSymptomsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.depressiveSymptomsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DepressiveSymptomsSessionUpdateComponent,
        resolve: {
            depressiveSymptomsSession: DepressiveSymptomsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.depressiveSymptomsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DepressiveSymptomsSessionUpdateComponent,
        resolve: {
            depressiveSymptomsSession: DepressiveSymptomsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.depressiveSymptomsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const depressiveSymptomsSessionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DepressiveSymptomsSessionDeletePopupComponent,
        resolve: {
            depressiveSymptomsSession: DepressiveSymptomsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.depressiveSymptomsSession.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
