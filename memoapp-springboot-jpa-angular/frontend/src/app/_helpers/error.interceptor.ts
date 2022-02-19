import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {catchError, first} from 'rxjs/operators';

import {AccountService, AlertService} from '../_services';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private accountService: AccountService,
                private alertService: AlertService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(errorResponse => {
            if (errorResponse.status === 401 || errorResponse.status === 403) {
                console.warn(`Error ${errorResponse.status} received: logging out for: `, request);
                // auto logout if 401 or 403 response returned from api

                if (this.accountService.userValue) {
                    this.accountService.logout().pipe(first())
                        .subscribe(() => {

                            this.alertService.error(`Automatic logout due to HTTP ${errorResponse.status} error: ` +
                                errorResponse.error.errorMessage || errorResponse.message || errorResponse.statusText);
                        });
                }
            }

            return throwError(errorResponse);
        }));
    }
}
