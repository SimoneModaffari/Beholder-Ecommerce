import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakInstance } from 'keycloak-js';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private keycloakInstance: KeycloakInstance;

  constructor(private keycloak: KeycloakService) {
    this.keycloakInstance = keycloak.getKeycloakInstance();
  }

  token = this.keycloak.getToken();

  isAuthenticated():boolean{
    return this.keycloak.isLoggedIn();
  }

  getUserEmail(): string  {
    if (this.keycloakInstance.tokenParsed) {
      return this.keycloakInstance.tokenParsed['email'];
    }
    return "null";
  }

  getUserRole():boolean{
    return this.keycloakInstance.hasResourceRole('admin');
  }
}
