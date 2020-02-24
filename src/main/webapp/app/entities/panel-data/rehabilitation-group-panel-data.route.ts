import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';
import { RehabilitationGroupService } from './rehabilitation-group-panel-data.service';
import { RehabilitationGroupPanelDataComponent } from './rehabilitation-group-panel-data.component';
import { RehabilitationGroupDetailPanelDataComponent } from './rehabilitation-group-detail.component';
import { IRehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';

@Injectable({ providedIn: 'root' })
export class RehabilitationGroupPanelDataResolve implements Resolve<IRehabilitationGroup> {
    constructor(private service: RehabilitationGroupService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRehabilitationGroup> {
        const id = route.params['id'];
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RehabilitationGroup>) => response.ok),
                map((rehabilitationGroup: HttpResponse<RehabilitationGroup>) => rehabilitationGroup.body)
            );
        }
        return of(new RehabilitationGroup());
    }
}

export const rehabilitationGroupPanelDataRoute: Routes = [
    {
        path: '',
        component: RehabilitationGroupPanelDataComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_MANAGER', 'ROLE_CONSULTANT'],
            pageTitle: 'cardioRehabCrApp.rehabilitationGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RehabilitationGroupDetailPanelDataComponent,
        resolve: {
            rehabilitationGroup: RehabilitationGroupPanelDataResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_MANAGER', 'ROLE_CONSULTANT'],
            pageTitle: 'cardioRehabCrApp.rehabilitationGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RehabilitationGroupPanelDataComponent,
        resolve: {
            rehabilitationGroup: RehabilitationGroupPanelDataResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_MANAGER', 'ROLE_CONSULTANT'],
            pageTitle: 'cardioRehabCrApp.rehabilitationGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
