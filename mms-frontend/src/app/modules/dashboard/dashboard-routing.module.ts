import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {  DashboardComponent } from './';
//import { LayoutComponent } from './layout.component';

const routes: Routes = [
    {
        path: 'dashboard',
        component: DashboardComponent,
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardRoutingModule {}
