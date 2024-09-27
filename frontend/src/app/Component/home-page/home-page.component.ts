import { Router } from '@angular/router';
import { HttpService } from './../../Service/http.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {

  constructor(private router: Router) { }

  goToShop() {
    this.router.navigate(['/shop/tutti/page/0']);
  }
}
