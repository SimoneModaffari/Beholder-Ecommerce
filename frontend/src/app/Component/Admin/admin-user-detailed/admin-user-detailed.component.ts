import { ActivatedRoute } from '@angular/router';
import { HttpService } from '../../../Service/http.service';
import { Cart, Order } from './../../../interfaces';
import { Component } from '@angular/core';


@Component({
  selector: 'app-admin-user-detailed',
  templateUrl: './admin-user-detailed.component.html',
  styleUrl: './admin-user-detailed.component.css'
})
export class AdminUserDetailedComponent {

  u : User={
    userID: 0,
    firstName: '',
    lastName: '',
    email: '',
    address: '',
    role: '',
    registrationDate: '',
    orders: [],
  }

  constructor(private route: ActivatedRoute, private httpj: HttpService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.u.userID = params['id'];
      this.httpj.getUserByID(this.u.userID).subscribe(
        (data: any) => {
          this.u = data;
          this.httpj.getUserOrdersForAdmin(this.u.email).subscribe(
            (res:any)=>{
              this.u.orders=res;
              console.log("ordini:"+this.u.orders)
            },
            (error)=>{
              console.error(error);
            }
          )
          console.log(this.u);
        },
        (error) => {
          console.error('Si è verificato un errore durante il recupero del cliente:', error);
        }
      );
    });
  }
}

export interface User {
  userID: number;
  firstName: string;
  lastName: string;
  email: string;
  address: string;
  role: string;
  registrationDate: string;
  orders: Order[];
}
