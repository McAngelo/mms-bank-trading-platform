import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {
  PortfolioDashboardComponent,
  PortfolioOverviewComponent,
  PortfolioOrdersComponent,
  PortfolioAccountsComponent
} from './';

const routes: Routes = [
  {
    path: 'portfolio',
    component: PortfolioDashboardComponent,
    children: [
      {
        path: 'overview',
        component: PortfolioOverviewComponent,
      },
      {
        path: 'orders/:id',
        component: PortfolioOrdersComponent,
      },
      {
        path: 'account-statement',
        component: PortfolioAccountsComponent
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
export class PortfolioRoutingModule {}
