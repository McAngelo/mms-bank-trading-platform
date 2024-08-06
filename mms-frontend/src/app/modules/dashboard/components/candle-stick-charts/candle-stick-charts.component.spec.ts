import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CandleStickChartsComponent } from './candle-stick-charts.component';

describe('CandleStickChartsComponent', () => {
  let component: CandleStickChartsComponent;
  let fixture: ComponentFixture<CandleStickChartsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CandleStickChartsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CandleStickChartsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
