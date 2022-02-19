import { Component } from '@angular/core';

import { AccountService } from './_services';
import { User } from './_models';

@Component({ selector: 'app', templateUrl: 'app.component.html' })
export class AppComponent {
    user: User;

    constructor(private accountService: AccountService) {
        this.accountService.user.subscribe(x => this.user = x);
    }

    get userIsAdmin(): boolean {
        return this.user
            && this.user.roles
            && this.user.roles.indexOf('ADMIN') >= 0;
    }

    logout() {
        this.accountService.logout();
    }
}