import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './Component/header/header.component';
import { HomePageComponent } from './Component/home-page/home-page.component';
import { HttpClientModule } from '@angular/common/http';
import { FooterComponent } from './Component/footer/footer.component';
import { ShopComponent } from './Component/shop/shop.component';
import { FormsModule } from '@angular/forms';
import { ProductPageComponent } from './Component/product-page/product-page.component';
import { initializeKeycloak } from './Utility/app.init';
import { RegistrationComponent } from './Component/registration/registration.component';
import { AddTocartComponent } from './Component/add-tocart/add-tocart.component';
import { CartComponent } from './Component/cart/cart.component';
import { ProductInOrderComponent } from './Component/product-in-order/product-in-order.component';
import { EditButtonComponent } from './Component/edit-button/edit-button.component';
import { PurchaseSuccessComponent } from './Component/purchase-success/purchase-success.component';
import { PurchaseFailureComponent } from './Component/purchase-failure/purchase-failure.component';
import { UnauthorizedComponent } from './Component/unauthorized/unauthorized.component';
import { AfterLoginComponent } from './Component/after-login/after-login.component';
import { AdminHeaderComponent } from './Component/Admin/admin-header/admin-header.component';
import { AdminHomeComponent } from './Component/Admin/admin-home/admin-home.component';
import { AdminOrdersComponent } from './Component/Admin/admin-orders/admin-orders.component';
import { RedirectComponent } from './Component/redirect/redirect.component';
import { AdminProductsComponent } from './Component/Admin/admin-products/admin-products.component';
import { ContactsComponent } from './Component/contacts/contacts.component';
import { OrderHistoryComponent } from './Component/order-history/order-history.component';
import { AdminProductEditComponent } from './Component/Admin/admin-product-edit/admin-product-edit.component';
import { AdminAddingProductComponent } from './Component/Admin/admin-adding-product/admin-adding-product.component';
import { AdminUsersComponent } from './Component/Admin/admin-users/admin-users.component';
import { AdminUserDetailedComponent } from './Component/Admin/admin-user-detailed/admin-user-detailed.component';
import { AdminOrderDetailComponent } from './Component/Admin/admin-order-detail/admin-order-detail.component';
import { FailureComponent } from './Component/failure/failure.component';





@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomePageComponent,
    FooterComponent,
    ShopComponent,
    ProductPageComponent,
    RegistrationComponent,
    AddTocartComponent,
    CartComponent,
    ProductInOrderComponent,
    EditButtonComponent,
    PurchaseSuccessComponent,
    PurchaseFailureComponent,

    UnauthorizedComponent,
    AfterLoginComponent,
    AdminHeaderComponent,
    AdminHomeComponent,
    AdminOrdersComponent,
    RedirectComponent,
    AdminProductsComponent,
    ContactsComponent,
    OrderHistoryComponent,
    AdminProductEditComponent,
    AdminAddingProductComponent,
    AdminUsersComponent,
    AdminUserDetailedComponent,
    AdminOrderDetailComponent,
    FailureComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    KeycloakAngularModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
