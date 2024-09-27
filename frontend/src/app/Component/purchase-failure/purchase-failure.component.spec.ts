import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchaseFailureComponent } from './purchase-failure.component';

describe('PurchaseFailureComponent', () => {
  let component: PurchaseFailureComponent;
  let fixture: ComponentFixture<PurchaseFailureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PurchaseFailureComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PurchaseFailureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
