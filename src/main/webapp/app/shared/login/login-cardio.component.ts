import { Component, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { LoginService } from 'app/core/login/login.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { ModalService } from 'app/shared/util/modal.service';
import { PasswordResetInitService } from 'app/account/password-reset/init/password-reset-init.service';
import { EMAIL_NOT_FOUND_TYPE } from 'app/shared/constants/error.constants';

@Component({
    selector: 'jhi-login',
    templateUrl: './login-cardio.html'
})
export class LoginComponent implements AfterViewInit {
    authenticationError: boolean;
    reseting = false;
    error: string;
    errorEmailNotExists: string;
    success: string;
    loginForm = this.fb.group({
        username: [''],
        password: [''],
        rememberMe: [false]
    });
    resetRequestForm = this.fb.group({
        email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]]
    });

    constructor(
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private stateStorageService: StateStorageService,
        private elementRef: ElementRef,
        private modal: ModalService,
        private renderer: Renderer,
        private router: Router,
        private fb: FormBuilder,
        private passwordResetInitService: PasswordResetInitService
    ) {}

    ngAfterViewInit() {
        setTimeout(() => this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []), 0);
    }

    login() {
        this.loginService
            .login({
                username: this.loginForm.get('username').value,
                password: this.loginForm.get('password').value,
                rememberMe: this.loginForm.get('rememberMe').value
            })
            .subscribe(
                user => {
                    this.authenticationError = false;
                    if (
                        this.router.url === '/account/register' ||
                        this.router.url.startsWith('/account/activate') ||
                        this.router.url.startsWith('/account/reset/')
                    ) {
                        this.router.navigate(['']);
                    }

                    this.eventManager.broadcast({
                        name: 'authenticationSuccess',
                        content: 'Sending Authentication Success'
                    });
                    switch (user.authorities[0]) {
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
                    // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                    // since login is successful, go to stored previousState and clear previousState
                    const redirect = this.stateStorageService.getUrl();
                    if (redirect) {
                        this.stateStorageService.storeUrl(null);
                        this.router.navigateByUrl(redirect);
                    }
                },
                () => {
                    this.authenticationError = true;
                    this.modal.message('Credenciales inválidos');
                }
            );
    }

    register() {
        this.router.navigate(['/account/register']);
    }

    requestReset() {
        this.error = null;
        this.errorEmailNotExists = null;

        this.passwordResetInitService.save(this.resetRequestForm.get(['email']).value).subscribe(
            () => {
                this.success = 'OK';
            },
            response => {
                this.success = null;
                if (response.status === 400 && response.error.type === EMAIL_NOT_FOUND_TYPE) {
                    this.errorEmailNotExists = 'ERROR';
                } else {
                    this.error = 'ERROR';
                }
            }
        );
    }

    requestResetPassword() {
        this.reseting = true;
        this.authenticationError = false;
        this.error = undefined;
        this.errorEmailNotExists = undefined;
        this.success = undefined;
        this.resetRequestForm = this.fb.group({
            email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]]
        });
    }
}
