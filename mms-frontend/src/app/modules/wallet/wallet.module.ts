import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/_metronic/shared/shared.module';
import { ModalsModule, WidgetsModule } from '../../_metronic/partials';
import { WalletComponent, Step1Component, Step2Component } from './';



@NgModule({
  declarations: [
    WalletComponent,
    Step1Component,
    Step2Component
  ],
  imports: [
    CommonModule,
    ModalsModule, 
    WidgetsModule,
    SharedModule
  ],
  exports:[
    WalletComponent
  ]
})
export class WalletModule { }
