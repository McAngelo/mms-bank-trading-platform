import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-trade-order-drawer',
  templateUrl: './trade-order-drawer.component.html',
  styleUrl: './trade-order-drawer.component.scss'
})
export class TradeOrderDrawerComponent implements OnInit {


  values:any = {};
  @Input() tradeOrder: any = {}

  itemClass: string = 'ms-1 ms-lg-3';
  btnClass: string = 'btn btn-icon btn-custom btn-icon-muted btn-active-light btn-active-color-primary w-35px h-35px w-md-40px h-md-40px';
  
  constructor() {}

  ngOnInit(): void {
    //console.log(this.tradeOrder);
  }
}

