import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';
import { RehabilitationGroupService } from './rehabilitation-group-evaluation.service';
import { RehabilitationGroupEvaluationComponent } from './rehabilitation-group-evaluation.component';
import { RehabilitationGroupDetailEvaluationComponent } from './rehabilitation-group-evaluation-detail.component';
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

export const rehabilitationGroupEvaluationRoute: Routes = [
    {
        path: '',
        component: RehabilitationGroupEvaluationComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_MANAGER', 'ROLE_CONSULTANT'],
            pageTitle: 'cardioRehabCrApp.rehabilitationGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RehabilitationGroupDetailEvaluationComponent,
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
        component: RehabilitationGroupEvaluationComponent,
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
