import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MayorEventsSession } from 'app/shared/model/mayor-events-session.model';
import { MayorEventsSessionService } from './mayor-events-session.service';
import { MayorEventsSessionComponent } from './mayor-events-session.component';
import { MayorEventsSessionDetailComponent } from './mayor-events-session-detail.component';
import { MayorEventsSessionUpdateComponent } from './mayor-events-session-update.component';
import { MayorEventsSessionDeletePopupComponent } from './mayor-events-session-delete-dialog.component';
import { IMayorEventsSession } from 'app/shared/model/mayor-events-session.model';

@Injectable({ providedIn: 'root' })
export class MayorEventsSessionResolve implements Resolve<IMayorEventsSession> {
    constructor(private service: MayorEventsSessionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMayorEventsSession> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MayorEventsSession>) => response.ok),
                map((mayorEventsSession: HttpResponse<MayorEventsSession>) => mayorEventsSession.body)
            );
        }
        return of(new MayorEventsSession());
    }
}

export const mayorEventsSessionRoute: Routes = [
    {
        path: '',
        component: MayorEventsSessionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.mayorEventsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MayorEventsSessionDetailComponent,
        resolve: {
            mayorEventsSession: MayorEventsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.mayorEventsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MayorEventsSessionUpdateComponent,
        resolve: {
            mayorEventsSession: MayorEventsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.mayorEventsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MayorEventsSessionUpdateComponent,
        resolve: {
            mayorEventsSession: MayorEventsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.mayorEventsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mayorEventsSessionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MayorEventsSessionDeletePopupComponent,
        resolve: {
            mayorEventsSession: MayorEventsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.mayorEventsSession.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
