import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { RoleRedirectService } from '../Service/role-redirect.service';


@Injectable({
  providedIn: 'root',
})
export class RoleRedirectGuard implements CanActivate {
  constructor(
    private router: Router,
    private keycloakService: KeycloakService,
    private roleRedirectService: RoleRedirectService
  ) {}

  async canActivate(): Promise<boolean> {
    const isLoggedIn = await this.keycloakService.isLoggedIn();
    if (isLoggedIn) {
      await this.roleRedirectService.redirectBasedOnRole();
      return false;
    }
    return true;
  }
}
