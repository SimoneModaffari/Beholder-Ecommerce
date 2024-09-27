import { Component } from '@angular/core';
import { HttpService } from '../../../Service/http.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-admin-order-detail',
  templateUrl: './admin-order-detail.component.html',
  styleUrl: './admin-order-detail.component.css'
})
export class AdminOrderDetailComponent {
  orderId: string='';
  order:Order={
    orderID: 0,
    total: 0,
    orderStatus: '',
    orderData: null,
    productsInOrder: []
  };

  constructor(private route: ActivatedRoute, private httpj: HttpService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.orderId = params['id'];

      this.httpj.getOrder(this.orderId).subscribe(
        (data: any) => {
          this.order = data;
          console.log(this.order);
        },
        (error) => {
          console.error('Si è verificato un errore durante il recupero del prodotto:', error);
        }
      );
    });
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
  orderData: Date | null;
  productsInOrder: OrderedProduct[];
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


