import { Component, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { LoginService } from 'app/core/login/login.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { ModalService } from 'app/shared/util/modal.service';
import { PasswordResetInitService } from 'app/account/password-reset/init/password-reset-init.service';
import { EMAIL_NOT_FOUND_TYPE } from 'app/shared/constants/error.constants';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';
import { JhiMainComponent } from 'app/layouts';

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
        private passwordResetInitService: PasswordResetInitService,
        public mainComponent: JhiMainComponent,
        public global: GlobalVariablesService
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
            .then(() => {
                this.authenticationError = false;
                if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                    this.router.navigate(['']);
                }

                this.eventManager.broadcast({
                    name: 'authenticationSuccess',
                    content: 'Sending Authentication Success'
                });
                this.mainComponent.setAccountLogin(this.loginForm.get('username').value);
                this.global.loadRehabId();

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is successful, go to stored previousState and clear previousState
                const redirect = this.stateStorageService.getUrl();
                if (redirect) {
                    this.stateStorageService.storeUrl(null);
                    this.router.navigate([redirect]);
                }
            })
            .catch(() => {
                this.authenticationError = true;
                this.modal.message('Credenciales inválidos');
            });
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
