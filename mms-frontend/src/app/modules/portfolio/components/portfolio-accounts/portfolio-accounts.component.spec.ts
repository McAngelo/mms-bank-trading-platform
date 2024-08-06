import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PortfolioAccountsComponent } from './portfolio-accounts.component';

describe('PortfolioAccountsComponent', () => {
  let component: PortfolioAccountsComponent;
  let fixture: ComponentFixture<PortfolioAccountsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PortfolioAccountsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PortfolioAccountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
