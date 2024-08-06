import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAccountProfileComponent } from './user-account-profile.component';

describe('UserAccountProfileComponent', () => {
  let component: UserAccountProfileComponent;
  let fixture: ComponentFixture<UserAccountProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserAccountProfileComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserAccountProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
