import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {  OrderListingsComponent, OrderDetailsComponent } from './';

const routes: Routes = [
    {
        path: 'trades',
        component: OrderListingsComponent,
    },
    {
        path: 'trades/:id',
        component: OrderDetailsComponent,
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OrdersRoutingModule {}