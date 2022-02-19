import { Component, OnInit } from '@angular/core';
import {first, map} from 'rxjs/operators';

import { AccountService } from '../_services';
import {User} from '../_models';

@Component({ templateUrl: 'list.component.html' })
export class ListComponent implements OnInit {

    users: UserVM[]|null = null;

    constructor(private accountService: AccountService) {}

    ngOnInit() {
        this.accountService.getAll()
            .pipe(first())
            .pipe(map(users => users.map(user => new UserVM(user))))
            .subscribe(users => this.users = users);
    }

    deleteUser(userToDelete: UserVM) {
        userToDelete.isDeleting = true;
        this.accountService.delete(userToDelete.loginId)
            .pipe(first())
            .subscribe(() => {
                this.users = this.users.filter(it => it.loginId !== userToDelete.loginId);
            });
    }
}

class UserVM extends User {

    isDeleting = false;

    constructor(user: User) {
        super();
        this.loginId = user.loginId;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.roles = user.roles;
    }
}
