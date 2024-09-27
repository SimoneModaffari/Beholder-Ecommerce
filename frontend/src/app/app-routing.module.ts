import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './Component/home-page/home-page.component';
import { ShopComponent } from './Component/shop/shop.component';
import { ProductPageComponent } from './Component/product-page/product-page.component';
import { AuthGuard } from './Utility/app.guard';
import { RegistrationComponent } from './Component/registration/registration.component';
import { CartComponent } from './Component/cart/cart.component';
import { PurchaseSuccessComponent } from './Component/purchase-success/purchase-success.component';
import { PurchaseFailureComponent } from './Component/purchase-failure/purchase-failure.component';
import { RoleRedirectGuard } from './Utility/RoleRedirectGuard';
import { UnauthorizedComponent } from './Component/unauthorized/unauthorized.component';
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

const routes: Routes = [
  { path: '', component: RedirectComponent },
  { path: 'home', component: HomePageComponent },
  { path: 'registrationFailed', component:FailureComponent},
  { path: 'afterLogin', canActivate: [RoleRedirectGuard], component: UnauthorizedComponent },
  {
    path: 'admin',
    component: AdminHomeComponent,
    canActivate: [AuthGuard],
    data: { roles: ['admin'] },
    children:[
      {path:'', redirectTo:'outlet', pathMatch:'full'},
      {
        path: 'admin-orders',
        outlet: 'content',
        component: AdminOrdersComponent
      },
      {
        path: 'admin-products',
        outlet: 'content',
        component: AdminProductsComponent
      },
      { path: 'admin-product-edit/:id',
        outlet:'content',
        component: AdminProductEditComponent, canActivate: [AuthGuard], data: { roles: ['admin'] }
      },
      { path: 'admin-product-add',
        outlet:'content',
        component: AdminAddingProductComponent, canActivate: [AuthGuard], data: { roles: ['admin'] }
      },
      { path: 'admin-users',
        outlet:'content',
        component: AdminUsersComponent, canActivate: [AuthGuard], data: { roles: ['admin'] }
      },
      { path: 'admin-users-detail/:id',
        outlet:'content',
        component: AdminUserDetailedComponent, canActivate: [AuthGuard], data: { roles: ['admin'] }
      },
      { path: 'admin-orders-detail/:id',
        outlet:'content',
        component: AdminOrderDetailComponent, canActivate: [AuthGuard], data: { roles: ['admin'] }
      },
    ]
  },

  { path: 'shop/:category/page/:page', component: ShopComponent},
  { path: 'product/:id', component: ProductPageComponent, canActivate: [AuthGuard]},
  { path: 'contatti', component:ContactsComponent},
  { path: 'register', component:RegistrationComponent},
  { path: 'login', component: HomePageComponent, canActivate: [AuthGuard]},
  { path: 'carrello', component:CartComponent, canActivate: [AuthGuard]},
  { path: 'purchaseSuccess', component:PurchaseSuccessComponent},
  { path: 'purchaseFailure', component:PurchaseFailureComponent},
  { path: 'storicoOrdini', component:OrderHistoryComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
