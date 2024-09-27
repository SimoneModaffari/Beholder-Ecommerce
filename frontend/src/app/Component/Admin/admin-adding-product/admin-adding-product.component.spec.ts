import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAddingProductComponent } from './admin-adding-product.component';

describe('AdminAddingProductComponent', () => {
  let component: AdminAddingProductComponent;
  let fixture: ComponentFixture<AdminAddingProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminAddingProductComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminAddingProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
