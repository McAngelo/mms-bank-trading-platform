import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExchangeMarketBriefComponent } from './exchange-market-brief.component';

describe('ExchangeMarketBriefComponent', () => {
  let component: ExchangeMarketBriefComponent;
  let fixture: ComponentFixture<ExchangeMarketBriefComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ExchangeMarketBriefComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ExchangeMarketBriefComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
