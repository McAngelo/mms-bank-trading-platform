import { Component } from '@angular/core';

@Component({
  selector: 'app-deactivate-user-account',
  templateUrl: './deactivate-user-account.component.html',
  styleUrl: './deactivate-user-account.component.scss'
})
export class DeactivateUserAccountComponent {
  constructor() {}

  saveSettings() {
    alert('Account has been successfully deleted!');
  }
}
