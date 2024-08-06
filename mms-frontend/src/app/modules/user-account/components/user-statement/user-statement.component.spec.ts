import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserStatementComponent } from './user-statement.component';

describe('UserStatementComponent', () => {
  let component: UserStatementComponent;
  let fixture: ComponentFixture<UserStatementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserStatementComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserStatementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
