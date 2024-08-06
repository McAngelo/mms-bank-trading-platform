import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeactivateUserAccountComponent } from './deactivate-user-account.component';

describe('DeactivateUserAccountComponent', () => {
  let component: DeactivateUserAccountComponent;
  let fixture: ComponentFixture<DeactivateUserAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeactivateUserAccountComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeactivateUserAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
