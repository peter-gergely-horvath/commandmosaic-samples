import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {BehaviorSubject, from, Observable} from 'rxjs';
import {map, tap} from 'rxjs/operators';

import { User } from '../_models';
import {CommandDispatcher} from '../_commandmosaic/command-dispatcher';
import {Command} from '../_commandmosaic/command';

@Injectable({ providedIn: 'root' })
export class AccountService {

    private readonly USER_LOCAL_STORE_KEY = 'user';

    private userSubject: BehaviorSubject<User>;
    public user: Observable<User>;

    constructor(
        private router: Router,

        private commandDispatcher: CommandDispatcher
    ) {
        this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem(this.USER_LOCAL_STORE_KEY)));
        this.user = this.userSubject.asObservable();
    }

    public get userValue(): User {
        return this.userSubject.value;
    }



    login(userName, userPassword) {
        this.clearUserState();

        return this.commandDispatcher.dispatchCommand<User>('auth/LoginCommand', null,
                {user: userName, password: userPassword}).pipe(
                    tap(user => localStorage.setItem(this.USER_LOCAL_STORE_KEY, JSON.stringify(user))),
                    tap(user => this.userSubject.next(user)));
    }

    logout(): Observable<boolean>  {
        this.clearUserState();
        return from(this.router.navigate(['/account/login']));
    }

    private clearUserState() {
        // remove user from local storage and set current user to null
        localStorage.removeItem(this.USER_LOCAL_STORE_KEY);
        this.userSubject.next(null);
    }

    register(theUser: User) {
        return this.commandDispatcher.dispatchCommand(
            'user/Register', { user : theUser});
    }

    getAll(): Observable<User[]> {
        return this.commandDispatcher.dispatchCommand<User[]>('user/FindAll');
    }

    getById(loginIdToGet: string) {
        return this.commandDispatcher.dispatchCommand<User>(
            'user/FindByLoginId', { loginId : loginIdToGet});
    }

    update(theLoginId, params) {

        const updatedUser = { ...{loginId: theLoginId}, ...params };

        return this.commandDispatcher.dispatchCommand<User>(
            'user/Update', { user : updatedUser })
            .pipe(map(x => {
                // update stored user if the logged in user updated their own record
                if (theLoginId === this.userValue.loginId) {
                    // update local storage
                    const user = { ...this.userValue, ...params };
                    localStorage.setItem(this.USER_LOCAL_STORE_KEY, JSON.stringify(user));

                    // publish updated user to subscribers
                    this.userSubject.next(user);
                }
                return x;
            }));
    }

    delete(loginIdToDelete: string) {
        return this.commandDispatcher.dispatchCommand(
            'user/DeleteByLoginId', { loginId : loginIdToDelete})
            .pipe(tap(it => {
                // auto logout if the logged in user deleted their own record
                if (loginIdToDelete === this.userValue.loginId) {
                    this.logout();
                }
            }));
    }
}
