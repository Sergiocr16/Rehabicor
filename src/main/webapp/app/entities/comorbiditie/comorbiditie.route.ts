import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Comorbiditie } from 'app/shared/model/comorbiditie.model';
import { ComorbiditieService } from './comorbiditie.service';
import { ComorbiditieComponent } from './comorbiditie.component';
import { ComorbiditieDetailComponent } from './comorbiditie-detail.component';
import { ComorbiditieUpdateComponent } from './comorbiditie-update.component';
import { ComorbiditieDeletePopupComponent } from './comorbiditie-delete-dialog.component';
import { IComorbiditie } from 'app/shared/model/comorbiditie.model';

@Injectable({ providedIn: 'root' })
export class ComorbiditieResolve implements Resolve<IComorbiditie> {
    constructor(private service: ComorbiditieService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IComorbiditie> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Comorbiditie>) => response.ok),
                map((comorbiditie: HttpResponse<Comorbiditie>) => comorbiditie.body)
            );
        }
        return of(new Comorbiditie());
    }
}

export const comorbiditieRoute: Routes = [
    {
        path: '',
        component: ComorbiditieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.comorbiditie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ComorbiditieDetailComponent,
        resolve: {
            comorbiditie: ComorbiditieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.comorbiditie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ComorbiditieUpdateComponent,
        resolve: {
            comorbiditie: ComorbiditieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.comorbiditie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ComorbiditieUpdateComponent,
        resolve: {
            comorbiditie: ComorbiditieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.comorbiditie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const comorbiditiePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ComorbiditieDeletePopupComponent,
        resolve: {
            comorbiditie: ComorbiditieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rehabicorApp.comorbiditie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
