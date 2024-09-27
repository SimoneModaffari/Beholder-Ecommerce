export interface Cart {
  idCarrello: number;
  dataCreazione: string;
  totale: number;
  idUtente: User;
  idOrdine: Order;
}
export interface Order {
  orderID: number;
  total: number;
  orderStatus: string;
  orderData: string;
  user: User;
  cart: Cart;
  productsInOrder: ProductInOrder[];
}

export interface ProductInOrder {
  productinOrderID: number;
  product: Product;
  order: Order;
  quantity: number;
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
  cart: Cart;
}

export interface Product {
  productID: number;
  name: string;
  descr: string;
  price: number;
  quantity: number;
  picture: string;
  category: string;
  idCategoria: String;
}
