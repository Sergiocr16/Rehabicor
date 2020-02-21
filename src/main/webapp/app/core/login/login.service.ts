import { Injectable } from '@angular/core';

import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';
import { flatMap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class LoginService {
    constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider) {}

    login(credentials) {
        return this.authServerProvider.login(credentials).pipe(flatMap(() => this.accountService.identity(true)));
    }

    loginWithToken(jwt, rememberMe) {
        return this.authServerProvider.loginWithToken(jwt, rememberMe);
    }

    logout() {
        this.authServerProvider.logout().subscribe(null, null, () => this.accountService.authenticate(null));
    }
}
