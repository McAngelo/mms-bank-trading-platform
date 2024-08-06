import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradeOrderDrawerComponent } from './trade-order-drawer.component';

describe('TradeOrderDrawerComponent', () => {
  let component: TradeOrderDrawerComponent;
  let fixture: ComponentFixture<TradeOrderDrawerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TradeOrderDrawerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TradeOrderDrawerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
