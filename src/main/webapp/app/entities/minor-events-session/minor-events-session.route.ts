import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MinorEventsSession } from 'app/shared/model/minor-events-session.model';
import { MinorEventsSessionService } from './minor-events-session.service';
import { MinorEventsSessionComponent } from './minor-events-session.component';
import { MinorEventsSessionDetailComponent } from './minor-events-session-detail.component';
import { MinorEventsSessionUpdateComponent } from './minor-events-session-update.component';
import { MinorEventsSessionDeletePopupComponent } from './minor-events-session-delete-dialog.component';
import { IMinorEventsSession } from 'app/shared/model/minor-events-session.model';

@Injectable({ providedIn: 'root' })
export class MinorEventsSessionResolve implements Resolve<IMinorEventsSession> {
    constructor(private service: MinorEventsSessionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMinorEventsSession> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MinorEventsSession>) => response.ok),
                map((minorEventsSession: HttpResponse<MinorEventsSession>) => minorEventsSession.body)
            );
        }
        return of(new MinorEventsSession());
    }
}

export const minorEventsSessionRoute: Routes = [
    {
        path: '',
        component: MinorEventsSessionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.minorEventsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MinorEventsSessionDetailComponent,
        resolve: {
            minorEventsSession: MinorEventsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.minorEventsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MinorEventsSessionUpdateComponent,
        resolve: {
            minorEventsSession: MinorEventsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.minorEventsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MinorEventsSessionUpdateComponent,
        resolve: {
            minorEventsSession: MinorEventsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.minorEventsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const minorEventsSessionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MinorEventsSessionDeletePopupComponent,
        resolve: {
            minorEventsSession: MinorEventsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.minorEventsSession.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
