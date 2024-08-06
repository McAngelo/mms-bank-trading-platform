import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortfolioRoutingModule } from './portfolio-routing.module';
import { 
  PortfolioDashboardComponent, 
  PortfolioCardComponent,
  PortfolioOverviewComponent,
  PortfolioOrdersComponent,
  PortfolioAccountsComponent
} from './';
import {
  CardsModule,
  DropdownMenusModule,
  WidgetsModule,
} from '../../_metronic/partials';
import { SharedModule } from "../../_metronic/shared/shared.module";



@NgModule({
  declarations: [
    PortfolioDashboardComponent,
    PortfolioCardComponent,
    PortfolioOverviewComponent,
    PortfolioOrdersComponent,
    PortfolioAccountsComponent
  ],
  imports: [
    CommonModule,
    CardsModule,
    DropdownMenusModule,
    WidgetsModule,
    SharedModule,
    PortfolioRoutingModule
  ]
})
export class PortfolioModule { }
