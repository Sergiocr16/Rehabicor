import { Injectable, OnInit, AfterContentChecked } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { AppUserService } from 'app/entities/app-user/app-user.service';
import { Account } from 'app/core/user/account.model';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { AppUser } from 'app/shared/model/app-user.model';
import { Subject } from 'rxjs';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';

@Injectable({ providedIn: 'root' })
export class GlobalVariablesService implements OnInit, AfterContentChecked {
    title = '';
    rehabCenterId = new Subject<any>();
    rehabCenter;
    rehabilitationCenters: IRehabilitationCenter[];
    isLoading = false;
    isInForm = false;
    isSaving;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    account: Account;
    userApp = new Subject<any>();

    constructor(
        private accountService: AccountService,
        private appUserService: AppUserService,
        private rehabilitationCenterService: RehabilitationCenterService
    ) {
        this.rehabilitationCenters = [];
        this.accountService.getAuthenticationState().subscribe(data => {
            if (data) {
                if (data.authorities[0] !== 'ROLE_ADMIN') {
                    this.defineGlobalRehabCenter();
                }
            }
        });
    }

    ngOnInit(): void {}

    ngAfterContentChecked() {
        // this.cdRef.detectChanges();
    }

    public setTitle(title) {
        this.title = title;
    }

    public loading() {
        this.isLoading = true;
    }

    public loaded() {
        this.isLoading = false;
    }

    public enteringForm() {
        this.isInForm = true;
    }

    public setFormStatus(isSaving) {
        this.isSaving = isSaving;
    }

    public leavingForm() {
        this.isInForm = false;
    }

    public getRehabilitationCenter() {
        return this.rehabilitationCenters;
    }
    public defineGlobalRehabCenter() {
        this.appUserService
            .findByCurrentUser()
            .pipe(
                filter((response: HttpResponse<AppUser>) => response.ok),
                map((appUser: HttpResponse<AppUser>) => appUser.body)
            )
            .subscribe(userApp => {
                this.userApp.next(userApp);
                const rehabCenterId = userApp.rehabilitationCenterId;
                this.rehabCenterId.next(rehabCenterId);
                this.rehabCenter = rehabCenterId;
            });
    }

    public paginateRehabilitationCenters(data: IRehabilitationCenter[]) {
        this.rehabilitationCenters = [];
        for (let i = 0; i < data.length; i++) {
            this.rehabilitationCenters.push(data[i]);
        }
        return this.rehabilitationCenters;
    }
}
