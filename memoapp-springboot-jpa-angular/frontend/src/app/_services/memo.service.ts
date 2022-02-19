import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {Memo} from '../_models/memo';
import {CommandDispatcher} from '../_commandmosaic/command-dispatcher';


@Injectable({providedIn: 'root'})
export class MemoService {

    constructor(
        private commandDispatcher: CommandDispatcher) {
    }

    getAllMemos(): Observable<Memo[]> {
        return this.commandDispatcher
            .dispatchCommand('memo/GetMemosOfCurrentUser', null);
    }

    getById(commandId: string): Observable<Memo> {
        return this.commandDispatcher
            .dispatchCommand<Memo>('memo/GetMemoById', {id: commandId})
    }

    create(memoText: string): Observable<Memo> {
        return this.commandDispatcher.dispatchCommand<Memo>(
            'memo/CreateMemo', {text: memoText});
    }

    update(theMemo: Memo): Observable<Memo> {
        return this.commandDispatcher
            .dispatchCommand<Memo>('memo/UpdateMemo', {memo: theMemo});
    }

    delete(memo: Memo): Observable<any> {
        return this.commandDispatcher
            .dispatchCommand('memo/DeleteMemoById', {id: memo.id});
    }
}
