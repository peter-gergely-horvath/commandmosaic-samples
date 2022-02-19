import {Injectable, Injector} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';

import {Command} from './command';
import {AccountService} from '../_services';
import {map} from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class CommandDispatcher {

    private accountService: AccountService|null = null;

    constructor(private http: HttpClient, private injector: Injector) { }

    dispatchCommand<T>(commandName: string, parameters?: {}, auth?: {}): Observable<T> {
        return this.dispatch(new Command<T>(commandName, parameters, auth));
    }

    private dispatch<T>(command: Command<T>): Observable<T> {
        // deferred fetch to avoid circular dependency issue
        if (!this.accountService) {
            this.accountService = this.injector.get(AccountService);
        }

        const user = this.accountService.userValue;
        const isLoggedIn = user && user.token;
        if (isLoggedIn) {
            if (!command.auth) {
                command.auth = {};
            }
            command.auth.token = user.token;
        }

        return this.http.post<any>(`commands`, command).pipe(map(response => response.result as T));
    }
}
