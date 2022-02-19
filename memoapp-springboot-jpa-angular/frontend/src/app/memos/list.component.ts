import {Component, OnDestroy, OnInit} from '@angular/core';
import {first} from 'rxjs/operators';

import {AccountService, AlertService} from '../_services';
import {User} from '../_models';
import {Memo} from '../_models/memo';
import {Subscription} from 'rxjs';
import {MemoService} from '../_services/memo.service';

@Component({templateUrl: 'list.component.html'})
export class ListComponent implements OnInit {
    user: User;

    memos: Memo[];

    constructor(private accountService: AccountService,
                private memoService: MemoService,
                private alertService: AlertService) {
        this.user = this.accountService.userValue;
    }

    ngOnInit() {
        this.loadMemos();
    }

    private loadMemos() {
        this.memoService.getAllMemos()
            .pipe(first())
            .subscribe(loadedMemos => this.memos = loadedMemos);
    }

    deleteMemo(memo: Memo) {
        this.memoService.delete(memo)
            .pipe(first())
            .subscribe(() => {
                    this.alertService.clear();
                    this.alertService.info('Memo deleted');
                    this.loadMemos();
                },
                error => {
                    this.alertService.error(error);
                });
    }
}
