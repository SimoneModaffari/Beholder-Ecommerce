import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../Service/http.service';
import { AuthService } from '../../Service/auth.service';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrl: './order-history.component.css'
})
export class OrderHistoryComponent implements OnInit{
  order:Order[]=[];

  constructor(private httpj:HttpService,private auth: AuthService){}
  ngOnInit(): void {
    this.httpj.getOrdersByUser().subscribe(
      (res: any) => {
        this.order = res;
        this.order.filter(order => order.orderStatus!='ACTIVE');

      },
    )
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
