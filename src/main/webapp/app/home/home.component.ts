import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account } from 'app/core';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private router: Router
    ) {}

    ngOnInit() {
        this.accountService.identity().then((account: Account) => {
            this.account = account;
            switch (account.authorities[0]) {
                case 'ROLE_ADMIN':
                    this.router.navigateByUrl('/admin/user-management');
                    break;
                case 'ROLE_MANAGER':
                    this.router.navigateByUrl('/panel-data');
                    break;
                case 'ROLE_CONSULTANT':
                    this.router.navigateByUrl('/panel-data');
                    break;
                case 'ROLE_USER':
                    this.router.navigateByUrl('/evaluation');
                    break;
            }
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
