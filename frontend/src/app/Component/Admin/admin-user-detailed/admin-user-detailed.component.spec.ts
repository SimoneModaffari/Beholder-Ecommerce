import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminUserDetailedComponent } from './admin-user-detailed.component';

describe('AdminUserDetailedComponent', () => {
  let component: AdminUserDetailedComponent;
  let fixture: ComponentFixture<AdminUserDetailedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminUserDetailedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminUserDetailedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
