import { Component, Input  } from '@angular/core';

@Component({
  selector: 'app-wallet-summary',
  templateUrl: './wallet-summary.component.html',
  styleUrl: './wallet-summary.component.scss'
})
export class WalletSummaryComponent {
  @Input() color: string = '';
  constructor() {}

}
