import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from '../../../Service/http.service';
import { product } from '../../shop/shop.component';

@Component({
  selector: 'app-admin-adding-product',
  templateUrl: './admin-adding-product.component.html',
  styleUrl: './admin-adding-product.component.css'
})
export class AdminAddingProductComponent {
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

  nuovoNome:string = '';
  nuovaDescr:string ='';
  nuovaQuantita:number=0;
  nuovaCategoria ='';
  nuovoPrezzo:number=0;
  nuovaImmagine:string='';
  flag:boolean=false;
  constructor(private route: ActivatedRoute, private httpj: HttpService) { }

  ngOnInit(): void {

  }

  onCategoryChange(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    this.nuovaCategoria = selectElement.value;
    console.log('Categoria selezionata:', this.nuovaCategoria);
  }

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];

    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        let base64String = reader.result as string;
        base64String = base64String.replace(/^data:image\/[a-z]+;base64,/, '');
        this.nuovaImmagine = base64String;
        console.log('Immagine convertita in base64 (senza prefisso):', this.nuovaImmagine);
      };
      reader.readAsDataURL(file);
    }
  }

  reload(){
    this.httpj.getProductByID(this.productId).subscribe(
      (data: any) => {
        this.p = data;
        console.log(this.p);

      },
      (error) => {
        console.error('Si è verificato un errore durante il recupero del prodotto:', error);
      }
    );
  }
  addProduct(){
    this.p ={
      productID: 0,
      name: this.nuovoNome,
      description: this.nuovaDescr,
      price: this.nuovoPrezzo,
      quantity: this.nuovaQuantita,
      picture: this.nuovaImmagine,
      category: this.nuovaCategoria
    }
    this.httpj.addProduct(this.p).subscribe(
      (res:any)=>{
        window.location.href = "/admin";
      },
      (error)=>{
        console.error(error);
        window.location.href = "/admin";
      })
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

