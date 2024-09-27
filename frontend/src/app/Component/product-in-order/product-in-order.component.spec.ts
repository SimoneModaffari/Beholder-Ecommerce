import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductInOrderComponent } from './product-in-order.component';

describe('ProductInOrderComponent', () => {
  let component: ProductInOrderComponent;
  let fixture: ComponentFixture<ProductInOrderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProductInOrderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProductInOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
