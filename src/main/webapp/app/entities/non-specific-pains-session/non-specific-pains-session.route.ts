import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NonSpecificPainsSession } from 'app/shared/model/non-specific-pains-session.model';
import { NonSpecificPainsSessionService } from './non-specific-pains-session.service';
import { NonSpecificPainsSessionComponent } from './non-specific-pains-session.component';
import { NonSpecificPainsSessionDetailComponent } from './non-specific-pains-session-detail.component';
import { NonSpecificPainsSessionUpdateComponent } from './non-specific-pains-session-update.component';
import { NonSpecificPainsSessionDeletePopupComponent } from './non-specific-pains-session-delete-dialog.component';
import { INonSpecificPainsSession } from 'app/shared/model/non-specific-pains-session.model';

@Injectable({ providedIn: 'root' })
export class NonSpecificPainsSessionResolve implements Resolve<INonSpecificPainsSession> {
    constructor(private service: NonSpecificPainsSessionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INonSpecificPainsSession> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<NonSpecificPainsSession>) => response.ok),
                map((nonSpecificPainsSession: HttpResponse<NonSpecificPainsSession>) => nonSpecificPainsSession.body)
            );
        }
        return of(new NonSpecificPainsSession());
    }
}

export const nonSpecificPainsSessionRoute: Routes = [
    {
        path: '',
        component: NonSpecificPainsSessionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.nonSpecificPainsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: NonSpecificPainsSessionDetailComponent,
        resolve: {
            nonSpecificPainsSession: NonSpecificPainsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.nonSpecificPainsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: NonSpecificPainsSessionUpdateComponent,
        resolve: {
            nonSpecificPainsSession: NonSpecificPainsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.nonSpecificPainsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: NonSpecificPainsSessionUpdateComponent,
        resolve: {
            nonSpecificPainsSession: NonSpecificPainsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.nonSpecificPainsSession.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nonSpecificPainsSessionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: NonSpecificPainsSessionDeletePopupComponent,
        resolve: {
            nonSpecificPainsSession: NonSpecificPainsSessionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.nonSpecificPainsSession.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
