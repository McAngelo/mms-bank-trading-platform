import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
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
}

@Component({
  selector: 'app-order-search',
  templateUrl: './order-search.component.html',
  styleUrl: './order-search.component.scss',
})
export class OrderSearchComponent implements OnInit, OnDestroy {
  showChangeEmailForm: boolean = false;
  showChangePasswordForm: boolean = false;
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

  orderIds: string[] = ['56037-XDER', '05822-FXSP', '4472-QREX', '00347-BCLQ'];
  tradesResult: TradeResult[] = [];

  constructor(private cdr: ChangeDetectorRef) {
    const loadingSubscr = this.isLoading$
      .asObservable()
      .subscribe((res) => (this.isLoading = res));
    this.unsubscribe.push(loadingSubscr);
  }

  ngOnInit(): void {
  }

  toggleEmailForm(show: boolean) {
    this.showChangeEmailForm = show;
  }

  saveEmail() {
    this.isLoading$.next(true);
    setTimeout(() => {
      this.isLoading$.next(false);
      this.showChangeEmailForm = false;
      this.cdr.detectChanges();
    }, 1500);
  }

  togglePasswordForm(show: boolean) {
    this.showChangePasswordForm = show;
  }

  savePassword() {
    this.isLoading$.next(true);
    setTimeout(() => {
      this.isLoading$.next(false);
      this.showChangePasswordForm = false;
      this.cdr.detectChanges();
    }, 1500);
  }

  ngOnDestroy() {
    this.unsubscribe.forEach((sb) => sb.unsubscribe());
  }
}
