
import { product } from './../Component/shop/shop.component';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserRegistrationRecord } from '../Component/registration/registration.component';
import { Product } from '../Component/product-page/product-page.component';
import { Cart } from '../interfaces';


@Injectable({
  providedIn: 'root'
})
export class HttpService {
  private url =  "http://localhost:8080/api"
  private auth:AuthService;
  constructor(private http: HttpClient, auth:AuthService ) {
    this.auth=auth;
   }

  /*Prodotti API*/
  getProducts(page:number, size:number) {
    return this.http.get(this.url+`/prodotti/get/?page=${page}&size=${size}`);
  }

  getProductsByCategory(categoria:String,page:String, size:String){
    return this.http.get(`${this.url}/prodotti/get/categoria?nomeCategoria=${categoria}&page=${page}&size=${size}`);
  }

  getProductsByCategoryAndPriceBetween(categoria: string, prezzoMin: number, prezzoMax: number,page:string,size:string ) {
    return this.http.get(this.url+`/prodotti/get/categoria/${categoria}/fasciaPrezzo?prezzo1=${prezzoMin}&prezzo2=${prezzoMax}&page=${page}&size=${size}`);
  }
  getProductsByPriceBetween(p1:number,p2:number, page:string,size:string){
    return this.http.get(this.url+`/prodotti/get/pBet?prezzo1=${p1}&prezzo2=${p2}&page=${page}&size=${size}`);
  }

  getProductsByPriceAsc(page:string,size:string){
    return this.http.get(this.url+`/prodotti/get/pAsc?page=${page}&size=${size}`);
  }

  findProductsByName(page:number, size:number, param:string){
    return this.http.get(this.url+`/prodotti/get/nome?nome=${param}&page=${page}&size=${size}`)
  }
  getProductsByPriceDesc(page:string,size:string){
    return this.http.get(this.url+`/prodotti/get/pDesc?page=${page}&size=${size}`);
  }
  getProductsByCategoryAndPriceAsc(categoria:string,page:string,size:string){
    return this.http.get(this.url+"/prodotti/get/categoria/"+categoria+`/prezzoAsc?page=${page}&size=${size}`);
  }
  getProductsByCategoryAndPriceDesc(categoria:string,page:string,size:string){
    return this.http.get(this.url+"/prodotti/get/categoria/"+categoria+`/prezzoDesc?page=${page}&size=${size}`);
  }
  getProductByID(id:string){
    return this.http.get(this.url+"/prodotti/get/"+id);
  }
  editProductPrice(id: number, newPrice: number): Observable<any> {
    return this.http.put<any>(`${this.url}/prodotti/${id}/editPrezzo`, newPrice);
  }
  editProductName(id: number, newName: string): Observable<any> {
    return this.http.put<any>(`${this.url}/prodotti/${id}/editNome`, newName);
  }
  editProductDescr(id: number, newDescr: string): Observable<any> {
    return this.http.put<any>(`${this.url}/prodotti/${id}/editDescr`, newDescr);
  }
  editProductCategory(id: number, newCat: string): Observable<any> {
    return this.http.put<any>(`${this.url}/prodotti/${id}/editCategory`, newCat);
  }
  editProductPicture(id: number, newPicture: string): Observable<any> {
    return this.http.put<any>(`${this.url}/prodotti/${id}/editPicture`, newPicture);
  }
  editProductQuantity(id: number, newPrice: number): Observable<any> {
    return this.http.put<any>(`${this.url}/prodotti/${id}/editPrezzo`, newPrice);
  }
  addProduct(p : Product){
    return this.http.post(`${this.url}/prodotti/add`, p);
  }
  deleteProduct(id:number){
    return this.http.delete(`${this.url}/prodotti/${id}/delete`)
  }
  /*User REST*/
  async registerUser(u: UserRegistrationRecord): Promise<boolean> {
    try {
      const res: any = await this.http.post(this.url + "/utenti/addUser", u).toPromise();
      console.log(res);
      return true;
    } catch (error) {
      console.error("errore" + error);
      return false;
    }
  }

  getAllUsers(page:number, size:number){
    return this.http.get(this.url+`/utenti/?page=${page}&size=${size}`);
  }
  findByEmail(page:number, size:number, param:string){
    return this.http.get(this.url+`/utenti/find?page=${page}&size=${size}&param=${param}`);
  }
  findUserEmailContaining(param:string){
    return this.http.get(this.url+`/utenti/getByEmailContaining/${param}`)
  }
  getUserByID(id:number){
    return this.http.get(this.url+`/utenti/get?id=${id}`);
  }

  addToCart(productId: number, quantity: number): Observable<any> {
    const url = `${this.url}/ordine/add`;
    const token = this.auth.token;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    const body = new URLSearchParams();
    body.set('id', productId.toString());
    body.set('quantity', quantity.toString());

    return this.http.post(url, body.toString(), { headers, responseType: 'text' });
  }

  getCart(email:String):Observable<Cart>{
    return this.http.get<Cart>(this.url+"/cart/"+email+"/carrello")
  }

  purchase(cartId: number, indirizzo: String, cap: String): Observable<any> {
    return this.http.post(`${this.url}/cart/purchase?cartId=${cartId}&indirizzo=${indirizzo}&cap=${cap}`, null,  { responseType: 'text' });
  }

  /*ProductInOrder REST*/

  getProductInOrder(id:number){
    return this.http.get(this.url+"/prodottiOrdinati/"+id);
  }

  removeProductInOrder(id:number){
    return this.http.delete(this.url+"/prodottiOrdinati/"+id+"/delete").subscribe(
      (res)=>{
        console.log(res);
      },
      (error)=>{
        console.error(error)
      }
    );
  }
  editProductInOrderQuantity(id:number, quantity:number){
    return this.http.put(this.url+"/prodottiOrdinati/"+id+"/editQuantita?q="+quantity,quantity).subscribe(
      (res)=>{
        console.log(res);
      },
      (error)=>{
        console.error(error)
      }
    );
  }

  //ORDERS REST
  getUserOrdersForAdmin(email:string){
    return this.http.get(this.url+`/ordine/adminUserOrder/${email}`)
  }

  getAllOrders(page: number, size: number){
    return this.http.get(`${this.url}/ordine/orders?page=${page}&size=${size}`);
  }

  getOrdersByUser(){
    const url = `${this.url}/ordine/userOrders`;
    const token = this.auth.token;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    const body = new URLSearchParams();
    return this.http.post(url, body.toString(), { headers, responseType: 'json' });
  }

  getOrder(id:string){
    return this.http.get(this.url+`/ordine/${id}`)
  }

}


