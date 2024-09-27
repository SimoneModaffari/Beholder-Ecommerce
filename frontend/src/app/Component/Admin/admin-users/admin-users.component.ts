import { Component } from '@angular/core';
import { HttpService } from '../../../Service/http.service';
import { User } from '../../../interfaces';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrl: './admin-users.component.css'
})
export class AdminUsersComponent {
  contentArray!: Page<UserDTO>;
  users: UserDTO[]=[];
  pagina:number = 0;
  size:number = 9;
  endpoint:string="";
  totalPages:number=0;
  searchParam:any ='';
  constructor(private httpj:HttpService){}
  ngOnInit(): void {
    this.httpj.getAllUsers(this.pagina, 9).subscribe(
      (data:any)=>{
        this.contentArray=data;
        this.users=this.contentArray.content;
      },
      (error)=>{
        console.error(error)
      }
    )
  }
  reloadParams(){
    this.users=this.contentArray.content;
    this.totalPages=this.contentArray.totalPages;
    this.endpoint="/orders/page/";
  }

  search(event: Event) {
    event.preventDefault();
    this.httpj.findUserEmailContaining(this.searchParam)
      .subscribe((data: any) => {
        this.users=data;
      });
  }
}

export interface Page<T> {
  content: UserDTO[]; // Lista degli elementi
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
interface UserDTO{
  userID:number,
  email:string,
  firstName:string,
  lastName:string
}
