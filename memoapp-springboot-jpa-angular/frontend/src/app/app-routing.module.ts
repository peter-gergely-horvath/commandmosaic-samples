import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AuthGuard} from './_helpers';

const routes: Routes = [
    {
        path: 'memos',
        loadChildren: () => import('./memos/memos.module').then(x => x.MemosModule),
        canActivate: [AuthGuard]
    },
    {
        path: 'users',
        loadChildren: () => import('./users/users.module').then(x => x.UsersModule),
    },
    {
        path: 'account',
        loadChildren: () => import('./account/account.module').then(x => x.AccountModule)
    },

    // otherwise redirect to memos
    { path: '**', redirectTo: 'memos' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
