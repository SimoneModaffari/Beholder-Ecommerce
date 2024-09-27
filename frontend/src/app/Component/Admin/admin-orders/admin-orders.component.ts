import { Component, OnInit } from '@angular/core';
import { Order } from '../../../interfaces';
import { HttpService } from '../../../Service/http.service';

@Component({
  selector: 'app-admin-orders',
  templateUrl: './admin-orders.component.html',
  styleUrl: './admin-orders.component.css'
})
export class AdminOrdersComponent implements OnInit{
  contentArray!: Page<Order>;
  orders: OrderDTO[]=[];
  pagina:number = 0;
  endpoint:string="";
  totalPages:number=0;
  constructor(private httpj:HttpService){}
  ngOnInit(): void {
    this.httpj.getAllOrders(this.pagina, 9).subscribe(
      (data:any)=>{
        this.contentArray=data;
        this.orders=this.contentArray.content;
        this.totalPages=this.contentArray.totalPages
      },
      (error)=>{
        console.error(error)
      }
    )
  }
  reloadParams(){
    this.httpj.getAllOrders(this.pagina, 9).subscribe(
      (data:any)=>{
        this.contentArray=data;
        this.orders=this.contentArray.content;
      })
  }
}
export interface Page<T> {
  content: OrderDTO[]; // Lista degli elementi
  pageable: {
    pageNumber: number; // Numero della pagina corrente
    pageSize: number; // Numero di elementi per pagina
    sort: any; // Opzioni di ordinamento (puoi definire un modello per questo se necessario)
    offset: number; // Offset del risultato rispetto al totale
    unpaged: boolean;
    paged: boolean;
  };
  last: boolean; // True se è l'ultima pagina
  totalElements: number; // Numero totale di elementi in tutte le pagine
  totalPages: number; // Numero totale di pagine
  size: number; // Numero di elementi nella pagina corrente
  number: number; // Numero della pagina corrente
  sort: any; // Opzioni di ordinamento (puoi definire un modello per questo se necessario)
  numberOfElements: number; // Numero di elementi nella pagina corrente
  first: boolean; // True se è la prima pagina
  empty: boolean; // True se la pagina è vuota
}

export interface OrderDTO {
  orderID: number;
  total: number;
  orderStatus: string;
  address: string;
  orderData: string;
  userID: number;
}
