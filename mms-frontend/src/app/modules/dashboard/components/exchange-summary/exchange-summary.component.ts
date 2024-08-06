import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-exchange-summary',
  templateUrl: './exchange-summary.component.html',
  styleUrl: './exchange-summary.component.scss'
})
export class ExchangeSummaryComponent {
  @Input() color: string = '';
  @Input() exchangeName: string = '';
  @Input() stocks: Array<any> = [];
  constructor() {}

}
