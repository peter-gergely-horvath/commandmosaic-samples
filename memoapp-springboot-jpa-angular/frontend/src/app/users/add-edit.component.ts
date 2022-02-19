import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AccountService, AlertService } from '../_services';

@Component({ templateUrl: 'add-edit.component.html' })
export class AddEditComponent implements OnInit {
    form: FormGroup;
    loginId: string;
    isAddMode: boolean;
    loading = false;
    submitted = false;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private accountService: AccountService,
        private alertService: AlertService
    ) {}

    ngOnInit() {
        this.loginId = this.route.snapshot.params['loginId'];
        this.isAddMode = !this.loginId;

        // password not required in edit mode
        const passwordValidators = [Validators.minLength(6)];
        if (this.isAddMode) {
            passwordValidators.push(Validators.required);
        }

        this.form = this.formBuilder.group({
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            loginId: ['', Validators.required],
            password: ['', passwordValidators],
            roles: ['', Validators.required],
        });

        if (!this.isAddMode) {
            this.accountService.getById(this.loginId)
                .pipe(first())
                .subscribe(x => {
                    this.f.firstName.setValue(x.firstName);
                    this.f.lastName.setValue(x.lastName);
                    this.f.loginId.setValue(x.loginId);
                    this.f.roles.setValue(x.roles);
                });
        }
    }

    // convenience getter for easy access to form fields
    get f() { return this.form.controls; }

    get isAdminUser(): boolean {
        const roles: string[] = this.f.roles.value as string[];
        return roles.indexOf('ADMIN') >= 0;
    }

    isAdminUserChanged(isAdminUser: boolean) {
        const roles: string[] = this.f.roles.value as string[];

        if (isAdminUser) {
            if (roles.indexOf('ADMIN') < 0) {
                roles.push('ADMIN');
                this.f.roles.setValue(roles);
            }
        } else {
            const index = roles.indexOf('ADMIN');
            if (index >= 0) {
                roles.splice(index, 1);
                this.f.roles.setValue(roles);
            }
        }
    }

    onSubmit() {
        this.submitted = true;

        // reset alerts on submit
        this.alertService.clear();

        // stop here if form is invalid
        if (this.form.invalid) {
            return;
        }

        this.loading = true;
        if (this.isAddMode) {
            this.createUser();
        } else {
            this.updateUser();
        }
    }



    private createUser() {
        this.accountService.register(this.form.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.alertService.success('User added successfully', { keepAfterRouteChange: true });
                    this.router.navigate(['.', { relativeTo: this.route }]);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }

    private updateUser() {
        this.accountService.update(this.loginId, this.form.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.alertService.success('Update successful', { keepAfterRouteChange: true });
                    this.router.navigate(['..', { relativeTo: this.route }]);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}
