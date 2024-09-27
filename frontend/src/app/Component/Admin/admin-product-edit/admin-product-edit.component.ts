import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpService } from '../../../Service/http.service';

@Component({
  selector: 'app-admin-product-edit',
  templateUrl: './admin-product-edit.component.html',
  styleUrl: './admin-product-edit.component.css'
})
export class AdminProductEditComponent {

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

  constructor(private route: ActivatedRoute, private httpj: HttpService, private router:Router) { }

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
  deleteProduct(){
    this.httpj.deleteProduct(this.p.productID).subscribe(
      (res:any)=>{
        console.log("Prodotto eliminato");
        this.router.navigate(['/admin-products']);
      },
      (error)=>{
        console.error(error);
        this.router.navigate(['/admin-products']);
      }
    )
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
  editNome(){
    this.httpj.editProductName(this.p.productID, this.nuovoNome).subscribe(
      (res:any)=>{
        console.log(res)
        this.reload();
      },
      (error)=>{
        console.error(error);
      }
    );

  }
  editDescr(){
    this.httpj.editProductDescr(this.p.productID, this.nuovaDescr).subscribe(
      (res:any)=>{
        console.log(res)
        this.reload();
      },
      (error)=>{
        console.error(error);
      }
    );
  }
  editPrezzo(){
    this.httpj.editProductPrice(this.p.productID, this.nuovoPrezzo).subscribe(
      (res:any)=>{
        console.log(res)
        this.reload();
      },
      (error)=>{
        console.error(error);
      }
    );
  }
  editQuantita(){
    this.httpj.editProductQuantity(this.p.productID, this.nuovaQuantita).subscribe(
      (res:any)=>{
        console.log(res)
        this.reload();
      },
      (error)=>{
        console.error(error);
      }
    );
  }
  editCategory(){
    this.httpj.editProductCategory(this.p.productID, this.nuovaCategoria).subscribe(
      (res:any)=>{
        console.log(res)
        this.reload();
      },
      (error)=>{
        console.error(error);
      }
    );
  }
  editImmagine(){
    this.httpj.editProductPicture(this.p.productID, this.nuovaImmagine).subscribe(
      (res:any)=>{
        console.log(res)
        this.reload();
      },
      (error)=>{
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

