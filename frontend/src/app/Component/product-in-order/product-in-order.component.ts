import { Router } from '@angular/router';
import { HttpService } from './../../Service/http.service';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';


@Component({
  selector: 'app-product-in-order',
  templateUrl: './product-in-order.component.html',
  styleUrl: './product-in-order.component.css'
})
export class ProductInOrderComponent implements OnInit, OnChanges {
  @Input() productId!: number |null;

  p: ProductInOrder={
    productInOrderID: 0,
    product: {
      productID: 0,
      name: '',
      descr: '',
      price: 0,
      quantity: 0,
      picture: '',
      category: '',
    },
    quantity: 0
  }

  quantity!:number

  constructor(private httpj:HttpService, private router: Router){

  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['productId']) {
      this.loadProduct();
    }
  }
  ngOnInit(): void {
    if(this.productId!= null && this.productId!=0){
      this.httpj.getProductInOrder(this.productId).subscribe(
        (res : any)=>{
          this.p=res;
          this.quantity = this.p.quantity;
        },
        (error)=>{
          console.error(error)
        }
      )
    }
  }

  loadProduct(): void {
    if (this.productId != null && this.productId != 0) {
      this.httpj.getProductInOrder(this.productId).subscribe(
        (res: any) => {
          this.p = res;
          this.quantity = this.p.quantity;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }
  increaseQuantity(){
    this.quantity++;
  }

  decreaseQuantity(){
    this.quantity--;
  }

  editQuantity(){
    if(this.productId!= null&& this.productId!=0){
      this.httpj.editProductInOrderQuantity(this.productId, this.quantity);
    }
  }

  removeFromOrder(){
    console.log(this.productId)
    if(this.productId!= null && this.productId!=0){
      this.httpj.removeProductInOrder(this.productId);
      window.location.href = "/carrello";
    }
  }

}


interface Product {
  productID: number;
  name: string;
  descr: string;
  price: number;
  quantity: number;
  picture: string;
  category: string;
}

interface ProductInOrder {
  productInOrderID: number;
  product: Product;
  quantity: number;
}
