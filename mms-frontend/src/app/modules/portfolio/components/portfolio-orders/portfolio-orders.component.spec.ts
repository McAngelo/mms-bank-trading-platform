import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PortfolioOrdersComponent } from './portfolio-orders.component';

describe('PortfolioOrdersComponent', () => {
  let component: PortfolioOrdersComponent;
  let fixture: ComponentFixture<PortfolioOrdersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PortfolioOrdersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PortfolioOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
