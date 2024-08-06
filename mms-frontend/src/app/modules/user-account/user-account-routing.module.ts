import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { 
    UserAccountProfileComponent, 
    UserSettingsComponent,
    UserOverviewComponent,
    UserStatementComponent,
    UserBillingComponent 
  } from './';

const routes: Routes = [
  {
    path: 'user-account',
    component: UserAccountProfileComponent,
    children: [
      {
        path: 'overview',
        component: UserOverviewComponent,
      },
      {
        path: 'settings',
        component: UserSettingsComponent,
      },
      {
        path: 'billing',
        component: UserBillingComponent,
      },
      {
        path: 'account-statement',
        component: UserStatementComponent,
      },
      { path: '', redirectTo: 'overview', pathMatch: 'full' },
      { path: '**', redirectTo: 'overview', pathMatch: 'full' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserAccountRoutingModule {}
