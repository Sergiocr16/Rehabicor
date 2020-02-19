import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MinorEvent } from 'app/shared/model/minor-event.model';
import { MinorEventService } from './minor-event.service';
import { MinorEventComponent } from './minor-event.component';
import { MinorEventDetailComponent } from './minor-event-detail.component';
import { MinorEventUpdateComponent } from './minor-event-update.component';
import { MinorEventDeletePopupComponent } from './minor-event-delete-dialog.component';
import { IMinorEvent } from 'app/shared/model/minor-event.model';

@Injectable({ providedIn: 'root' })
export class MinorEventResolve implements Resolve<IMinorEvent> {
    constructor(private service: MinorEventService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMinorEvent> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MinorEvent>) => response.ok),
                map((minorEvent: HttpResponse<MinorEvent>) => minorEvent.body)
            );
        }
        return of(new MinorEvent());
    }
}

export const minorEventRoute: Routes = [
    {
        path: '',
        component: MinorEventComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.minorEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MinorEventDetailComponent,
        resolve: {
            minorEvent: MinorEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.minorEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MinorEventUpdateComponent,
        resolve: {
            minorEvent: MinorEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.minorEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MinorEventUpdateComponent,
        resolve: {
            minorEvent: MinorEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.minorEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const minorEventPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MinorEventDeletePopupComponent,
        resolve: {
            minorEvent: MinorEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.minorEvent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
