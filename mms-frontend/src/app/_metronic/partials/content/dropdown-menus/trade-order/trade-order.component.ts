import { Component, HostBinding, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-trade-order',
  templateUrl: './trade-order.component.html',
})
export class TradeOrderComponent implements OnInit {
  @HostBinding('class') class =
    'menu menu-sub menu-sub-dropdown w-250px w-md-300px';
  @HostBinding('attr.data-kt-menu') dataKtMenu = 'true';

  values:any = {};
  @Input() tradeOrder: any = {}

  constructor() {}

  ngOnInit(): void {
    //console.log(this.tradeOrder);
  }
}
