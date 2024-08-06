import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface ExchangeMarketBrief {
  name?: string;
  symbol?: string;
  icon?: string;
  askPrice?: number;
  bidPrice?: number;
  avgSell?: number;
  avgBuy?: number;
  maxPriceShift?: number;
  lastTradedPrice?: number;
}

interface StockData {
  TICKER: string;
  SELL_LIMIT: number;
  LAST_TRADED_PRICE: number;
  ASK_PRICE: number;
  BID_PRICE: number;
  BUY_LIMIT: number;
  MAX_PRICE_SHIFT: number;
}

export interface ExchangeData {
  [key: string]: StockData[];
}

@Injectable({
  providedIn: 'root'
})
export class DashboardStoreService {

  private _exchangeData = new BehaviorSubject<ExchangeData>({});
  private _dataStore: { exchangeData: ExchangeData } = { exchangeData: {} };
  readonly exchangeData = this._exchangeData.asObservable();

  constructor() { }

  public readAll(){
    this._exchangeData.next(Object.assign({}, this._dataStore).exchangeData);
  }

  public create(exchangeData: ExchangeData) {
    this._dataStore.exchangeData = {};
    this._dataStore.exchangeData = exchangeData;
    sessionStorage.setItem('exchangeData', JSON.stringify(exchangeData));
    this._exchangeData.next(Object.assign({}, this._dataStore).exchangeData);
  }


  public update(key: string, dataObj: ExchangeData){
    //let exchangeData: ExchangeData = JSON.parse(sessionStorage.getItem('exchangeData') || '{}');
    this._dataStore.exchangeData = dataObj;
    sessionStorage.setItem(key, JSON.stringify(dataObj));
    this._exchangeData.next(Object.assign({}, this._dataStore).exchangeData);
  }

  public remove(){
    this._dataStore.exchangeData = {};
    sessionStorage.removeItem('exchangeData')
    //this._exchangeData.next(Object.assign({}, this._dataStore).exchangeData);
  }
}
