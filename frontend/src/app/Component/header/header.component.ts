import { Component, HostListener, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { AuthService } from '../../Service/auth.service';
import { KeycloakService } from 'keycloak-angular';
import { HttpService } from '../../Service/http.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  isLoggedIn:boolean = false;
  larghezzaFinestra: number;
  constructor(private a:AuthService, private keycloakService: KeycloakService, private httpj:HttpService, private router:Router) {
    this.larghezzaFinestra = window.innerWidth;
  }
  ngOnInit(): void {
    console.log(this.larghezzaFinestra);
    this.isLoggedIn=this.a.isAuthenticated();
    this.larghezzaFinestra=window.innerWidth
  }


  @HostListener('window:resize', ['$event'])
  onResize() {
    console.log('La finestra è stata ridimensionata!');
    this.larghezzaFinestra=window.innerWidth;
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    const primoHeader = document.querySelector('.primoheader');
    const secondoHeader = document.querySelector('.secondoHeader');
    if (!primoHeader || !secondoHeader) return;

    const primoHeaderRect = primoHeader.getBoundingClientRect();
    const secondoHeaderRect = secondoHeader.getBoundingClientRect();
    const scrollPos = window.scrollY;

    const primoHeaderTop = primoHeaderRect.top + window.scrollY;
    const primoHeaderBottom = primoHeaderRect.bottom + window.scrollY;
    const headerHeight = primoHeaderRect.height;

    if (scrollPos >= primoHeaderTop && scrollPos <= primoHeaderBottom) {
        secondoHeader.classList.remove('secondoHeaderSticky');
        primoHeader.classList.remove('hideHeader');
    } else if (scrollPos > primoHeaderBottom) {
        secondoHeader.classList.add('secondoHeaderSticky');
        primoHeader.classList.add('hideHeader');
    } else {
        secondoHeader.classList.remove('secondoHeaderSticky');
        primoHeader.classList.remove('hideHeader');
    }
  }

  login() {
    this.router.navigate(['/login'])
  }

  storicoOrdini(){
    this.router.navigate(['/storicoOrdini']);
  }

  logout() {
    this.router.navigate(['/home']).then(()=> this.keycloakService.logout().then(() => this.keycloakService.clearToken()));
  }
}
