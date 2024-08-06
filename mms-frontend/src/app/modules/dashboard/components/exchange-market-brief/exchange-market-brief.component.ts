import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { DashboardStoreService, ExchangeData, ExchangeMarketBrief } from '../../../../shared';

import { Observable, of, Subscription } from 'rxjs';

@Component({
  selector: 'app-exchange-market-brief',
  templateUrl: './exchange-market-brief.component.html',
  styleUrl: './exchange-market-brief.component.scss'
})
export class ExchangeMarketBriefComponent  implements OnInit, OnDestroy {
  @Input() color: string = '';
  @Input() exchangeName: string = '';
  marketBriefs$: Observable<ExchangeMarketBrief[]>;

  public exchangeDataObj$!: Observable<ExchangeData>;
  public objSubscription!: Subscription;

  exchangeMarketBrief = [
    {
      name: 'Microsoft',
      symbol: 'MSFT',
      icon: './assets/media/svg/brand-logos/microsoft-5.svg',
      askPrice: 0.0,
      bidPrice: 0.0,
      avgSell: 0.0,
      avgBuy: 0.0,
      maxPriceShift: 0.0,
      lastTradedPrice: 0.0
    },
    {
      name: 'Amazon',
      symbol: 'AMZN',
      icon: './assets/media/svg/brand-logos/amazon.svg',      
      askPrice: 0.0,
      bidPrice: 0.0,
      avgSell: 0.0,
      avgBuy: 0.0,
      maxPriceShift: 0.0,
      lastTradedPrice: 0.0
    },
    {
      name: 'NetFlix',
      symbol: 'NFLX',
      icon: './assets/media/svg/brand-logos/netflix.svg',
      askPrice: 0.0,
      bidPrice: 0.0,
      avgSell: 0.0,
      avgBuy: 0.0,
      maxPriceShift: 0.0,
      lastTradedPrice: 0.0
    },
    {
      name: 'Google',
      symbol: 'GOOGL',
      icon: './assets/media/svg/brand-logos/google-icon.svg',
      askPrice: 0.0,
      bidPrice: 0.0,
      avgSell: 0.0,
      avgBuy: 0.0,
      maxPriceShift: 0.0,
      lastTradedPrice: 0.0
    },
    {
      name: 'Apple',
      symbol: 'AAPL',
      icon: './assets/media/svg/brand-logos/apple-black.svg',
      askPrice: 0.0,
      bidPrice: 0.0,
      avgSell: 0.0,
      avgBuy: 0.0,
      maxPriceShift: 0.0,
      lastTradedPrice: 0.0
    },
    {
      name: 'Tesla',
      symbol: 'TSLA',
      icon: './assets/media/svg/brand-logos/tesla.svg',
      askPrice: 0.0,
      bidPrice: 0.0,
      avgSell: 0.0,
      avgBuy: 0.0,
      maxPriceShift: 0.0,
      lastTradedPrice: 0.0
    },
    {
      name: 'Oracle',
      symbol: 'ORCL',
      icon: './assets/media/svg/brand-logos/oracle.svg',
      askPrice: 0.0,
      bidPrice: 0.0,
      avgSell: 0.0,
      avgBuy: 0.0,
      maxPriceShift: 0.0,
      lastTradedPrice: 0.0
    },
    {
      name: 'IBM',
      symbol: 'IBM',
      icon: './assets/media/svg/brand-logos/ibm-logo.svg',
      askPrice: 0.0,
      bidPrice: 0.0,
      avgSell: 0.0,
      avgBuy: 0.0,
      maxPriceShift: 0.0,
      lastTradedPrice: 0.0
    },
  ];

  constructor(private _dashboardStoreService: DashboardStoreService) {}

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    this.exchangeDataObj$ = this._dashboardStoreService.exchangeData;
    this.objSubscription = this.exchangeDataObj$.subscribe((data: ExchangeData) => {
      console.log(data);
      if(this.exchangeName == "EXCHANGE 1"){
        this.marketBriefs$ = of( this.updateExchangeMarketBrief(data.EXCHANGE1, this.exchangeMarketBrief));
        console.log(`${this.exchangeName} Market brief`, this.marketBriefs$);
      }
      
      if(this.exchangeName == "EXCHANGE 2"){
        this.marketBriefs$ = of(this.updateExchangeMarketBrief(data.EXCHANGE2, this.exchangeMarketBrief));
        console.log(`${this.exchangeName} Market brief`, this.marketBriefs$);
      }
      
    });
    
  }

  updateExchangeMarketBrief(exchangeBrief: any, exchangeMarketBrief: any): any {
    
    if(exchangeBrief && Object.keys(exchangeBrief).length != 0){
      exchangeMarketBrief.forEach((market:any) => {
        const marketR = exchangeBrief.find((brief:any) => brief.TICKER === market.symbol );
        if (marketR) {
          market.askPrice = marketR.ASK_PRICE;
          market.bidPrice = marketR.BID_PRICE;
          market.maxPriceShift = marketR.MAX_PRICE_SHIFT;
          market.lastTradedPrice = marketR.LAST_TRADED_PRICE;
          market.avgSell = marketR.SELL_LIMIT; 
          market.avgBuy = marketR.BUY_LIMIT;
        }
      });
    }
    console.log("EX 1 DATA", exchangeMarketBrief);
  return exchangeMarketBrief;

}

  ngOnDestroy(): void {
    this.objSubscription.unsubscribe();
  }

}
