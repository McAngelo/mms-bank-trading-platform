import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DropdownMenusModule, WidgetsModule } from '../../_metronic/partials';
import {SharedModule} from "../../_metronic/shared/shared.module";
import { UserAccountRoutingModule } from './user-account-routing.module';
import { 
  UserAccountProfileComponent, 
  UserSettingsComponent,
  UserOverviewComponent,
  UserStatementComponent,
  UserBillingComponent, 
  EditUserProfileComponent,
  ChangeEmailPasswordComponent,
  DeactivateUserAccountComponent
} from './';


@NgModule({
  declarations: [
    UserAccountProfileComponent,
    UserSettingsComponent,
    UserOverviewComponent,
    UserStatementComponent,
    UserBillingComponent,
    EditUserProfileComponent,
    ChangeEmailPasswordComponent,
    DeactivateUserAccountComponent
  ],
  imports: [
    CommonModule,
    DropdownMenusModule, 
    WidgetsModule,
    SharedModule,
    UserAccountRoutingModule
  ]
})
export class UserAccountModule { }
