
import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../Service/http.service';
import { AuthService } from '../../Service/auth.service';
import { Router } from '@angular/router';



@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit {

  c: Cart={
    cartID: 0,
    userID: {
      userID: 0,
      firstName: '',
      lastName: '',
      email: '',
      address: '',
      role: '',
      registrationDate: '',
      orders: []
    },
    orderID:{
      orderID: 0,
      total: 0,
      orderStatus: '',
      orderDate: null,
      productsInOrder: [],
      address: "",
    }
  };
  required:boolean = false;
  indirizzo:string ="";
  stato:string ="";
  regione:string ="";
  cap:string ="";
  numeroArticoli : number=0;
  loading: boolean = false;
  pagamentoSelezionato: boolean = false;
  constructor(private httpj: HttpService, private auth: AuthService,private router: Router) {
   }

  ngOnInit(): void {
    this.loading=true;
    this.httpj.getCart(this.auth.getUserEmail()).subscribe(
      (res: any) => {
        this.c = res;
        console.log(this.c);
        this.numeroArticoli = this.c.orderID.productsInOrder.length;
        this.loading=false;
      },
      (err) => {
        console.error(err);
      }
    );
  }


  open: boolean = false;
  selectedProductId: number =0;

  openModal(productId: number): void {
    this.selectedProductId = productId;
    this.open = true;
  }

  closeModal(): void {
    this.open = false;
    this.selectedProductId = 0;
  }

  reload(){
    this.loading=true;
    this.open=false;
    this.httpj.getCart(this.auth.getUserEmail()).subscribe(
      (res: any) => {
        this.c = res;
        console.log(this.c);
        this.loading=false;
      },
      (err) => {
        console.error(err);
      }
    );
  }
  ngAfterViewInit(): void {
    this.applyBootstrapValidation();
  }

  applyBootstrapValidation(): void {
    (() => {
      'use strict';

      const forms = document.querySelectorAll('.needs-validation');
      Array.from(forms).forEach(form => {
        const htmlForm = form as HTMLFormElement;
        htmlForm.addEventListener('submit', event => {
          if (!htmlForm.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
          }

          htmlForm.classList.add('was-validated');
        }, false);
      });
    })();
  }

  isEverythingFulfilled():boolean{
    if(this.stato==''||this.regione==''||this.cap==''||this.indirizzo=='' ||this.pagamentoSelezionato==false) return this.required=false;
    else{
      return this.required=true;
    }
  }
  purchase() {
    console.log("Indirizzo"+this.indirizzo)
    return this.httpj.purchase(this.c.cartID, this.indirizzo,this.cap).subscribe(
      (res)=>{
        console.log(res)
        this.router.navigate(['/purchaseSuccess']);
      },
      (err)=>{
        console.error(err)
        this.router.navigate(['/purchaseFailure']);
      }
    )
  }



  aggiornaMetodoPagamento(event: any) {
    this.pagamentoSelezionato = event.target.checked;
  }
}


interface Product {
  productID: number;
  name: string;
  picture: string;
  price:number
}

interface OrderedProduct {
  productInOrderID: number;
  product: Product;
  quantity: number;
}

interface Order {
  orderID: number;
  total: number;
  orderStatus: string;
  orderDate: Date | null;
  productsInOrder: OrderedProduct[];
  address: string;
}

interface User {
  userID: number;
  firstName: string;
  lastName: string;
  email: string;
  address: string;
  role: string;
  registrationDate: String;
  orders: Order[];
}

interface Cart {
  cartID: number;
  userID: User;
  orderID: Order;
}
