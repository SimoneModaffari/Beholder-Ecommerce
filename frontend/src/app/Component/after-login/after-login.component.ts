import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-after-login',
  template: '<p>Redirecting...</p>',
})
export class AfterLoginComponent implements OnInit {
  constructor(private router: Router, private keycloakService: KeycloakService) {}

  async ngOnInit() {
    const roles = this.keycloakService.getUserRoles();

    if (roles.includes('admin')) {
      await this.router.navigate(['/admin']);
    }
  }
}
