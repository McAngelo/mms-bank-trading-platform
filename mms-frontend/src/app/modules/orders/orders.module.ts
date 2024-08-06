import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrdersRoutingModule } from './orders-routing.module';
import { DataTablesModule } from 'angular-datatables';
import { ModalsModule, WidgetsModule } from '../../_metronic/partials';
import { SharedModule } from 'src/app/_metronic/shared/shared.module';
import { DropdownMenusModule } from '../../_metronic/partials/content/dropdown-menus/dropdown-menus.module';
import { OrderListingsComponent, OrderDetailsComponent } from './';
import { OrderSearchComponent } from './components/order-search/order-search.component';



@NgModule({
  declarations: [
    OrderListingsComponent,
    OrderDetailsComponent,
    OrderSearchComponent
  ],
  imports: [
    CommonModule,
    OrdersRoutingModule,
    WidgetsModule,
    ModalsModule,
    SharedModule,
    DataTablesModule,
    DropdownMenusModule
  ]
})
export class OrdersModule { }
