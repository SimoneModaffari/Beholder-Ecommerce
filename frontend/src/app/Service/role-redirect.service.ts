import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class RoleRedirectService {
  constructor(private router: Router, private keycloakService: KeycloakService) {}

  async redirectBasedOnRole() {
    const roles = this.keycloakService.getUserRoles();

    if (roles.includes('admin')) {
      this.router.navigate(['/admin']);
    } else if (roles.includes('customer')) {
      this.router.navigate(['/home']);
    } else {
      this.router.navigate(['/unauthorized']);
    }
  }
}
