import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MayorEvent } from 'app/shared/model/mayor-event.model';
import { MayorEventService } from './mayor-event.service';
import { MayorEventComponent } from './mayor-event.component';
import { MayorEventDetailComponent } from './mayor-event-detail.component';
import { MayorEventUpdateComponent } from './mayor-event-update.component';
import { MayorEventDeletePopupComponent } from './mayor-event-delete-dialog.component';
import { IMayorEvent } from 'app/shared/model/mayor-event.model';

@Injectable({ providedIn: 'root' })
export class MayorEventResolve implements Resolve<IMayorEvent> {
    constructor(private service: MayorEventService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMayorEvent> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MayorEvent>) => response.ok),
                map((mayorEvent: HttpResponse<MayorEvent>) => mayorEvent.body)
            );
        }
        return of(new MayorEvent());
    }
}

export const mayorEventRoute: Routes = [
    {
        path: '',
        component: MayorEventComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.mayorEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MayorEventDetailComponent,
        resolve: {
            mayorEvent: MayorEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.mayorEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MayorEventUpdateComponent,
        resolve: {
            mayorEvent: MayorEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.mayorEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MayorEventUpdateComponent,
        resolve: {
            mayorEvent: MayorEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.mayorEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mayorEventPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MayorEventDeletePopupComponent,
        resolve: {
            mayorEvent: MayorEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.mayorEvent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
