import { ChangeDetectorRef, AfterViewInit, Component, EventEmitter, Input, OnDestroy, OnInit, Output, Renderer2, TemplateRef, ViewChild } from '@angular/core';
import { SweetAlertOptions } from 'sweetalert2';
import moment from 'moment';
import { BehaviorSubject, Subscription } from 'rxjs';

interface Stock {
  name: string;
  symbol: string;
  icon: string;
}

interface OrderType {
  id: number;
  name: string;
}

interface TradeType {
  id: number;
  name: string;
}

interface OrderStatus {
  id: number;
  name: string;
}

interface Portofolio {
  id: number;
  name: string;
}

interface TradeResult {
  orderId: string;
  stock: Stock;
  portfolio: Portofolio;
  orderType: OrderType;
  tradeType: TradeType;
  status: OrderStatus;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
  date: Date
}

@Component({
  selector: 'app-order-listings',
  templateUrl: './order-listings.component.html',
  styleUrl: './order-listings.component.scss'
})
export class OrderListingsComponent implements OnInit, OnDestroy {

  
  isLoading$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  isLoading: boolean;
  values: any = '';
  private unsubscribe: Subscription[] = [];

  portfolios: Portofolio[] = [
    { id: 1, name: 'Basic' },
    { id: 2, name: 'TurnTabl Trades' },
  ];

  stocks: Stock[] = [
    {
      name: 'Microsoft',
      symbol: 'MSFT',
      icon: './assets/media/svg/brand-logos/microsoft-5.svg',
    },
    {
      name: 'Amazon',
      symbol: 'AMZN',
      icon: './assets/media/svg/brand-logos/amazon.svg',
    },
    {
      name: 'NetFlix',
      symbol: 'NFLX',
      icon: './assets/media/svg/brand-logos/netflix.svg',
    },
    {
      name: 'Google',
      symbol: 'GOOGL',
      icon: './assets/media/svg/brand-logos/google-icon.svg',
    },
    {
      name: 'Apple',
      symbol: 'AAPL',
      icon: './assets/media/svg/brand-logos/apple-black.svg',
    },
    {
      name: 'Tesla',
      symbol: 'TSLA',
      icon: './assets/media/svg/brand-logos/tesla.svg',
    },
    {
      name: 'Oracle',
      symbol: 'ORCL',
      icon: './assets/media/svg/brand-logos/oracle.svg',
    },
    {
      name: 'IBM',
      symbol: 'IBM',
      icon: './assets/media/svg/brand-logos/ibm-logo.svg',
    },
  ];

  orderTypes: OrderType[] = [
    { id: 1, name: 'Single' },
    { id: 2, name: 'Split' },
  ];

  tradeTypes: TradeType[] = [
    { id: 1, name: 'Market' },
    { id: 2, name: 'Limit' },
  ];

  orderStatuses: OrderStatus[] = [
    { id: 1, name: 'Open' },
    { id: 2, name: 'Cancelled' },
    { id: 3, name: 'Filled' },
    { id: 4, name: 'Failed' },
  ];

  tradesResult: TradeResult[] = [];

  datatableConfig: DataTables.Settings = {};

  constructor(private cdr: ChangeDetectorRef) {
    const loadingSubscr = this.isLoading$
      .asObservable()
      .subscribe((res) => (this.isLoading = res));
    this.unsubscribe.push(loadingSubscr);
  }

  ngOnInit(): void {
    this.generateTradesResult(10);
  }

  
  getRandomElement<T>(array: T[]): T {
    return array[Math.floor(Math.random() * array.length)];
  }

  getRandomValue(min: number, max: number): number {
    return parseFloat((Math.random() * (max - min) + min).toFixed(2));
  }

  getRandomDigit(): string {
    return Math.floor(Math.random() * 10).toString();
}

getRandomLetter(): string {
    const letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    return letters[Math.floor(Math.random() * letters.length)];
}

 generateRandomId(): string {
    let digits = "";
    for (let i = 0; i < 5; i++) {
        digits += this.getRandomDigit();
    }

    let letters = "";
    for (let i = 0; i < 4; i++) {
        letters += this.getRandomLetter();
    }

    return `${digits}-${letters}`;
}

getRandomDate(start: Date, end: Date): Date {
  const startTime = start.getTime();
  const endTime = end.getTime();
  const randomTime = new Date(startTime + Math.random() * (endTime - startTime));
  return randomTime;
}

formatDate(date: Date): string {
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
}



  generateTradesResult(num: number): TradeResult[] {
    // Generate a random date between May 1, 2024, and today
    const startDate = new Date('2024-05-01');
    const endDate = new Date(); // Today's date


    const tradesResult: TradeResult[] = [];
    for (let i = 0; i < num; i++) {
      const stock = this.getRandomElement(this.stocks);
      const orderType = this.getRandomElement(this.orderTypes);
      const tradeType = this.getRandomElement(this.tradeTypes);
      const status = this.getRandomElement(this.orderStatuses);
      const quantity = Math.floor(this.getRandomValue(1, 1000));
      const unitPrice = this.getRandomValue(5, 200);
      const totalPrice = parseFloat((quantity * unitPrice).toFixed(2));
      const portfolio = this.getRandomElement(this.portfolios);
      const randomDate = this.getRandomDate(startDate, endDate);
      const formattedRandomDate = this.formatDate(randomDate);

      this.tradesResult.push({
        orderId: this.generateRandomId(),
        stock,
        portfolio,
        orderType,
        tradeType,
        status,
        quantity,
        unitPrice,
        totalPrice,
        date:randomDate
      });
    }
    return tradesResult;
  }

  ngOnDestroy() {
    this.unsubscribe.forEach((sb) => sb.unsubscribe());
  }

}
