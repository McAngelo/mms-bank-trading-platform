import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExchangeSummaryComponent } from './exchange-summary.component';

describe('ExchangeSummaryComponent', () => {
  let component: ExchangeSummaryComponent;
  let fixture: ComponentFixture<ExchangeSummaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ExchangeSummaryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ExchangeSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
