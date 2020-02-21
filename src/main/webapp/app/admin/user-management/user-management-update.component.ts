import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { AppUserService } from 'app/entities/app-user/app-user.service';
import { ModalService } from 'app/shared/util/modal.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
    selector: 'jhi-user-mgmt-update',
    templateUrl: './user-management-update.component.html'
})
export class UserMgmtUpdateComponent implements OnInit, OnDestroy {
    user: User;
    appUser;
    languages: any[];
    authorities: any[];
    isSaving: boolean;
    title;
    modalSuccessMessage;
    rehabilitationcenters: IRehabilitationCenter[];
    confirmMessage;

    editForm = this.fb.group({
        id: [null],
        login: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*')]],
        firstName: ['', [Validators.required, Validators.maxLength(50)]],
        lastName: ['', [Validators.required, Validators.maxLength(50)]],
        email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
        activated: [true],
        // langKey: [[], Validators.required,],
        authorities: [[], Validators.required],
        rehabilitationCenterId: [[], Validators.required]
    });

    constructor(
        private languageHelper: JhiLanguageHelper,
        private userService: UserService,
        private route: ActivatedRoute,
        private fb: FormBuilder,
        private global: GlobalVariablesService,
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected appUserService: AppUserService,
        protected modal: ModalService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ user }) => {
            this.user = user.body ? user.body : user;
            if (user.id != null) {
                this.appUserService.findByUser(user.id).subscribe(appUser => {
                    this.appUser = appUser.body;
                    this.updateForm(this.user, this.appUser);
                });
            } else {
                this.appUser = { id: null };
                this.updateForm(this.user, this.appUser);
            }
            this.title = !this.user.id ? 'Crear un usuario' : 'Editar un usuario';
            this.modalSuccessMessage = !this.user.id ? 'Usuario creado correctamente.' : 'Usuario editado correctamente.';
            this.global.setTitle(this.title);
            this.confirmMessage = !user.id ? 'new' : 'update';
        });
        this.global.enteringForm();

        this.authorities = [];
        this.userService.authorities().subscribe(authorities => {
            this.authorities = authorities;
        });

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

    setInvalidForm(isSaving) {
        this.global.setFormStatus(isSaving);
    }

    ngOnDestroy() {
        this.global.leavingForm();
    }

    private updateForm(user, appUser): void {
        this.editForm.patchValue({
            id: user.id,
            login: user.login,
            firstName: user.firstName,
            lastName: user.lastName,
            email: user.email,
            activated: user.activated,
            // langKey: user.langKey,
            authorities: user.authorities,
            rehabilitationCenterId: appUser.rehabilitationCenterId
        });
    }

    previousState() {
        window.history.back();
    }

    getRole(authority) {
        switch (authority) {
            case 'ROLE_ADMIN':
                return 'Superadministrador';
                break;
            case 'ROLE_MANAGER':
                return 'Administrador de centro';
                break;
            case 'ROLE_USER':
                return 'Evaluador de consultas';
                break;
            case 'ROLE_CONSULTANT':
                return 'Consultor';
                break;
        }
    }

    save() {
        this.modal.confirmDialog(this.confirmMessage, () => {
            this.isSaving = true;
            this.updateUser(this.user, this.appUser);
            if (this.user.id !== null) {
                this.userService.update(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
            } else {
                this.userService.create(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
            }
        });
    }

    private updateUser(user: User, appUser): void {
        user.login = this.editForm.get(['login']).value;
        user.firstName = this.editForm.get(['firstName']).value;
        user.lastName = this.editForm.get(['lastName']).value;
        user.email = this.editForm.get(['email']).value;
        user.activated = this.editForm.get(['activated']).value;
        // user.langKey = this.editForm.get(['langKey']).value;
        user.authorities = this.editForm.get(['authorities']).value;
        appUser.rehabilitationCenterId = this.editForm.get(['rehabilitationCenterId']).value;
        // appUser.name = this.editForm.get(['firstName']).value;
        // appUser.lastName = this.editForm.get(['lastName']).value;
        appUser.authorityType = this.defineAuthority(this.editForm.get(['authorities']).value);
        appUser.status = this.editForm.get(['activated']).value ? 1 : 0;
        appUser.userLogin = this.editForm.get(['login']).value;
        appUser.userId = user.id;
    }

    private defineAuthority(authorities) {
        switch (authorities[0]) {
            case 'ROLE_ADMIN':
                return 1;
            case 'ROLE_MANAGER':
                return 2;
            case 'ROLE_USER':
                return 3;
            case 'ROLE_CONSULTANT':
                return 4;
        }
    }

    private onSaveSuccess(result) {
        if (this.appUser.id !== null) {
            this.appUserService.update(this.appUser).subscribe(
                response => {
                    this.isSaving = false;
                    this.modal.message(this.modalSuccessMessage);
                    this.previousState();
                },
                () => this.onSaveError()
            );
        } else {
            this.appUser.userId = result.id;
            this.appUserService.create(this.appUser).subscribe(
                response => {
                    this.isSaving = false;
                    this.modal.message(this.modalSuccessMessage);
                    this.previousState();
                },
                () => this.onSaveError()
            );
        }
    }

    protected onError(errorMessage: string) {
        // this.jhiAlertService.error(errorMessage, null, null);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    trackRehabilitationCenterById(index: number, item: IRehabilitationCenter) {
        return item.id;
    }
}
