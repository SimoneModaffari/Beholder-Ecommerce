import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { HttpService } from '../../Service/http.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent {


  firstName: string='';
  lastName: string='';
  email: string='';
  password: string='';
  address:string = '';
  confirmPassword: string='';
  agreedToTerms: boolean=false;
  popupShow:boolean =false;
  constructor(private httpj:HttpService, private router:Router) {

  }


  async registerUser() {
    const userRegistrationRecord: UserRegistrationRecord = {
      username: this.email,
      email: this.email,
      firstName: this.firstName,
      lastName: this.lastName,
      address:this.address,
      password: this.password
    };
    const flag = this.httpj.registerUser(userRegistrationRecord);
    if (await flag) {
    this.router.navigate(['/home']);
    } else {
      this.router.navigate(['/registrationFailed'])
    }
  }

}

export interface UserRegistrationRecord {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  address: string;
  password: string;
}

