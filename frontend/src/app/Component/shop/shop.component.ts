import { ProductInOrder } from './../../interfaces';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { HttpService } from '../../Service/http.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrl: './shop.component.css'
})
export class ShopComponent {
  productArray: product[] = [];
  contentArray!: Page<product>;
  sorted:boolean = false;
  category = "";
  minPrezzo: number = 0;
  maxPrezzo: number=0;
  selectedOption:string;
  pagina:number = 0;
  size :number = 9;
  endpoint:string="";
  totalPages:number=0;
  searchParam:string ='';
  loading: boolean = false;
  constructor(private httpj: HttpService, private route: ActivatedRoute){
    this.selectedOption='';

  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      console.log(params)
      this.category = params['category'];
      console.log(this.category);
      if(this.sorted==true){
        this.handleOptionChange();
      }else{
        if (this.category === 'tutti') {
          this.loading = true;
          this.httpj.getProducts(this.pagina,this.size).subscribe((data:any)=>{
            this.contentArray=data;
            this.reloadParams();
            this.loading = false;
          });
        } else {
          this.loading = true;
          this.httpj.getProductsByCategory(this.category,this.pagina.toString(),this.size.toString()).subscribe((data:any)=>{
            this.contentArray=data;
            this.reloadParams();
            this.loading = false;
          });
        }
      }
    });
  }

  reloadParams(){
    this.productArray=this.contentArray.content;
    this.totalPages=this.contentArray.totalPages;
    this.endpoint="/shop/"+this.category+"/page/";
  }
  handleOptionChange() {
    this.loading = true;
    if (this.selectedOption === 'prezzoCrescente') {
      if(this.category==='tutti'){
        this.httpj.getProductsByPriceAsc(this.pagina.toString(),this.size.toString()).subscribe((data: any) => {
          this.contentArray=data;
          this.reloadParams();
          this.loading = false;
        });
      }else{
        this.httpj.getProductsByCategoryAndPriceAsc(this.category,this.pagina.toString(),this.size.toString()).subscribe((data: any) => {
          this.contentArray=data;
          this.reloadParams();
          this.loading = false;
        });
      }

    } else if (this.selectedOption === 'prezzoDecrescente') {
      if(this.category==='tutti'){
        this.httpj.getProductsByPriceDesc(this.pagina.toString(),this.size.toString()).subscribe((data: any) => {
          this.contentArray=data;
          this.reloadParams();
          this.loading = false;
        });
      }else{
        this.httpj.getProductsByCategoryAndPriceDesc(this.category,this.pagina.toString(),this.size.toString()).subscribe((data: any) => {
          this.contentArray=data;
          this.reloadParams();
          this.loading = false;
        });
      }
    }
  }

  search(event: Event) {
    event.preventDefault();
    this.loading = true;
    this.httpj.findProductsByName(this.pagina,this.size, this.searchParam)
      .subscribe((data: any) => {
        this.contentArray = data;
        this.reloadParams();
        this.loading = false;
      });
  }
  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}

export interface product{
  productID: number,
  name:string,
  description:string,
  price:number,
  quantity:number,
  picture:string,
  category:string|null
}

export interface Page<T> {
  content: product[]; // Lista degli elementi
  pageable: {
    pageNumber: number; // Numero della pagina corrente
    pageSize: number; // Numero di elementi per pagina
    sort: any; // Opzioni di ordinamento
    offset: number; // Offset del risultato rispetto al totale
    unpaged: boolean;
    paged: boolean;
  };
  last: boolean; // True se è l'ultima pagina
  totalElements: number; // Numero totale di elementi in tutte le pagine
  totalPages: number; // Numero totale di pagine
  size: number; // Numero di elementi nella pagina corrente
  number: number; // Numero della pagina corrente
  sort: any; // Opzioni di ordinamento
  numberOfElements: number; // Numero di elementi nella pagina corrente
  first: boolean; // True se è la prima pagina
  empty: boolean; // True se la pagina è vuota
}
