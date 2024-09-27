import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontEndProgetto';
  constructor(private keycloakService: KeycloakService){

  }

  getUserRole(){
    const roles = this.keycloakService.getUserRoles();
    if (roles.includes('admin')) {
      return true
    }
    else{
      return false
    }
  }
}
