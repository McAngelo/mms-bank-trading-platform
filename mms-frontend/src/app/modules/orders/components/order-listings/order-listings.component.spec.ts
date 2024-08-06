import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderListingsComponent } from './order-listings.component';

describe('OrderListingsComponent', () => {
  let component: OrderListingsComponent;
  let fixture: ComponentFixture<OrderListingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrderListingsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OrderListingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
