import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from './app-user.service';
import { IUser, UserService } from 'app/core';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center';

@Component({
    selector: 'jhi-app-user-update',
    templateUrl: './app-user-update.component.html'
})
export class AppUserUpdateComponent implements OnInit {
    appUser: IAppUser;
    isSaving: boolean;

    users: IUser[];

    rehabilitationcenters: IRehabilitationCenter[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected appUserService: AppUserService,
        protected userService: UserService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ appUser }) => {
            this.appUser = appUser;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.rehabilitationCenterService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRehabilitationCenter[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRehabilitationCenter[]>) => response.body)
            )
            .subscribe(
                (res: IRehabilitationCenter[]) => (this.rehabilitationcenters = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.appUser.id !== undefined) {
            this.subscribeToSaveResponse(this.appUserService.update(this.appUser));
        } else {
            this.subscribeToSaveResponse(this.appUserService.create(this.appUser));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppUser>>) {
        result.subscribe((res: HttpResponse<IAppUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackRehabilitationCenterById(index: number, item: IRehabilitationCenter) {
        return item.id;
    }
}
