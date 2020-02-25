import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError, ActivatedRoute, NavigationStart } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { map } from 'rxjs/operators';
import { LoginService } from 'app/core/login/login.service';

import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { Account } from 'app/core/user/account.model';
import { GlobalVariablesService } from '../../shared/util/global-variables.service';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
    loadedAccount = false;
    mode = 'side';
    opened = true;
    layoutGap = '64';
    fixedInViewport = true;
    title = '';
    isCreatingNewPassWord = false;
    account: any;

    constructor(
        private gv: GlobalVariablesService,
        private jhiLanguageHelper: JhiLanguageHelper,
        private router: Router,
        private route: ActivatedRoute,
        private bpo: BreakpointObserver,
        private loginService: LoginService
    ) {}

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        // let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'cardioRehabCrApp';
        // if (routeSnapshot.firstChild) {
        //   title = this.getPageTitle(routeSnapshot.firstChild) || title;
        // }
        return 'RehabiCor';
    }

    ngOnInit() {
        this.loadedAccount = this.loginService.isAuthenticated();
        this.loginService.identity().then((account: Account) => {
            this.loadedAccount = true;
            if (account) {
                this.setAccountLogin(account.login);
            }
        });
        const breakpoints = Object.keys(Breakpoints).map(key => Breakpoints[key]);
        this.bpo
            .observe(breakpoints)
            .pipe(map(bst => bst.matches))
            .subscribe(matched => {
                this.determineSidenavMode();
                this.determineLayoutGap();
            });

        this.router.events.subscribe((event: NavigationStart) => {
            if (event instanceof NavigationEnd) {
                this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
            if (event instanceof NavigationError && event.error.status === 404) {
                this.router.navigate(['/404']);
            }
            const state = event.url.split('?')[0];
            this.isCreatingNewPassWord = state === '/reset/finish';
        });
    }

    isAuthenticated() {
        return this.loginService.isAuthenticated();
    }

    isAccountChecked() {
        return false;
    }

    setAccountLogin(username) {
        this.account = username;
    }
    private determineSidenavMode(): void {
        if (this.isExtraSmallDevice() || this.isSmallDevice()) {
            this.fixedInViewport = false;
            this.mode = 'over';
            this.opened = false;
            return;
        }

        this.fixedInViewport = true;
        this.mode = 'side';
    }

    private determineLayoutGap(): void {
        if (this.isExtraSmallDevice() || this.isSmallDevice()) {
            this.layoutGap = '0';
            return;
        } else {
            this.opened = true;
        }
        this.layoutGap = '64';
    }

    public isExtraSmallDevice(): boolean {
        return this.bpo.isMatched(Breakpoints.XSmall);
    }

    public isSmallDevice(): boolean {
        return this.bpo.isMatched(Breakpoints.Small);
    }

    logout() {
        this.loginService.logout();
        this.router.navigate(['']);
    }

    public getTitle() {
        return this.gv.title;
    }

    public isLoading() {
        return this.gv.isLoading;
    }

    public collapseNavBar() {
        this.opened = !this.opened;
    }

    public isInForm() {
        return this.gv.isInForm;
    }

    public isSaving() {
        return this.gv.isSaving;
    }

    public previousState() {
        window.history.back();
    }
}
