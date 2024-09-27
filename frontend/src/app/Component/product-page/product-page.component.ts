import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from '../../Service/http.service';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrl: './product-page.component.css'
})
export class ProductPageComponent  implements OnInit{

  productId: string = '';
  p: Product ={
    productID: 0,
    name: '',
    description: '',
    price: 0,
    quantity: 0,
    picture: '',
    category: null
  }

  quantity:  number = 1;

  constructor(private route: ActivatedRoute, private httpj: HttpService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.productId = params['id'];

      this.httpj.getProductByID(this.productId).subscribe(
        (data: any) => {
          this.p = data;
          console.log(this.p);
        },
        (error) => {
          console.error('Si è verificato un errore durante il recupero del prodotto:', error);
        }
      );
    });
  }

  increaseQuantity(){
    this.quantity++;
  }

  decreaseQuantity(){
    this.quantity--;
  }

  addToCart(){
    return this.httpj.addToCart(this.p.productID,this.quantity).subscribe(
      (res:any)=>{
        console.log(res)
      },
      (error:any)=>{
        console.error(error);
      }
    );
  }
}

export interface Product {
  productID: number,
  name: string,
  description: string,
  price: number,
  quantity: number,
  picture: string,
  category: string | null
}
